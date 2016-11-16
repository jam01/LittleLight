package com.jam01.littlelight.adapter.android.presentation.inventory;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jam01.littlelight.R;
import com.jam01.littlelight.adapter.android.LittleLight;
import com.jam01.littlelight.adapter.android.presentation.user.CircleTransform;
import com.jam01.littlelight.adapter.android.presentation.user.UserActivity;
import com.jam01.littlelight.adapter.android.utils.ItemClickSupport;
import com.jam01.littlelight.adapter.android.utils.SelectableAdapter;
import com.jam01.littlelight.adapter.common.presentation.InventoryDPO;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.inventory.Inventory;
import com.jam01.littlelight.domain.inventory.Item;
import com.jam01.littlelight.domain.inventory.ItemBag;
import com.jam01.littlelight.domain.legend.Character;
import com.jam01.littlelight.domain.legend.Legend;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InventoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InventoryFragment extends Fragment implements InventoryPresenter.InventoryView {
    private static final String TAG = "InventoryFragment";
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MEMBERSHIP_TYPE = "param1";
    private static final String MEMBERSHIP_ID = "param2";
    private InventoryPresenter presenter;
    private ViewPager mPager;
    private TabLayout tabs;
    private SwipeRefreshLayout swipeContainer;
    private AccountId accountId;
    private ActionMode actionMode = null;
    private Map<String, ItemBagView> bagViewMap;
    private List<ItemBagDestination> destinations;

    public InventoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param accountId Parameter 1.
     * @return A new instance of fragment BlankFragment.
     */
    public static InventoryFragment newInstance(AccountId accountId) {
        InventoryFragment fragment = new InventoryFragment();
        Bundle args = new Bundle();
        args.putInt(MEMBERSHIP_TYPE, accountId.withMembershipType());
        args.putString(MEMBERSHIP_ID, accountId.withMembershipId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            accountId = new AccountId(getArguments().getInt(MEMBERSHIP_TYPE), getArguments().getString(MEMBERSHIP_ID));
        }

        if (presenter == null) {
            presenter = ((LittleLight) getActivity().getApplication()).getComponent().provideInventoryPresenter();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_generic, container, false);
        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        tabs = ((UserActivity) getActivity()).getTabs();
        tabs.removeAllTabs();

        ((UserActivity) getActivity()).getSupportActionBar().setTitle("Inventory");

        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(() -> presenter.refresh(accountId));

        mPager.setOffscreenPageLimit(3);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                Single.defer(() -> Single.just(Picasso.with(getContext()).load(characters.get(finalI).emblemPath()).get())
//                        .subscribeOn(Schedulers.io()))
//                        .doOnSuccess(bitmap -> tabs.getTabAt(finalI).setIcon(new BitmapDrawable(bitmap)));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //See: http://stackoverflow.com/questions/25978462/swiperefreshlayout-viewpager-limit-horizontal-scroll-only
                swipeContainer.setEnabled(state == ViewPager.SCROLL_STATE_IDLE);
            }
        });
        tabs.setupWithViewPager(mPager);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.bindView(this);
        presenter.onStart(accountId);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser && actionMode != null) {
            actionMode.finish();
        }
    }

    @Override
    public void onStop() {
        presenter.unbindView();
        super.onStop();
    }

    @Override
    public void renderInventory(InventoryDPO inventoryDPO) {
        Inventory inventory = inventoryDPO.inventory;
        Legend legend = inventoryDPO.legend;

        List<ItemBagView> bagViews = new ArrayList<>(inventory.allItemBags().size());
        bagViewMap = new HashMap<>(bagViews.size());
        ItemClickListener clickListener = new ItemClickListener();
        ItemLongClickListener longClickListener = new ItemLongClickListener();

        destinations = new ArrayList<>(bagViews.size());

        for (ItemBag bag : inventory.allItemBags()) {
            ItemBagView bagView = new ItemBagView(bag, getContext());
            ItemClickSupport.addTo(bagView)
                    .setOnItemClickListener(clickListener)
                    .setOnItemLongClickListener(longClickListener);
            bagViews.add(bagView);
            bagViewMap.put(bag.withId(), bagView);

            //Populate destinationsList
            if (bag instanceof com.jam01.littlelight.domain.inventory.Character)
                for (Character character : legend.withCharacters()) {
                    if (character.characterId().equals(((com.jam01.littlelight.domain.inventory.Character) bag).characterId())) {
                        destinations.add(new ItemBagDestination(character.name(), character.emblemPath(), character.emblemBackgroundPath(), bag.withId()));
                        break;
                    }
                }
            else
                destinations.add(new ItemBagDestination("Vault", null, null, bag.withId()));
        }

        mPager.setAdapter(new ItemBagViewPagerAdapter(bagViews));

        //Set icons on tabs
        List<Character> characters = new ArrayList<>(legend.withCharacters());
        for (int i = 0; i < tabs.getTabCount(); i++) {
            tabs.getTabAt(i).setCustomView(R.layout.view_tablayout_tab);
            if (i < tabs.getTabCount() - 1)
                Picasso.with(getContext())
                        .load(characters.get(i).emblemPath())
                        .transform(new CircleTransform())
                        .fit()
                        .into((ImageView) tabs.getTabAt(i)
                                .getCustomView()
                                .findViewById(R.id.ivTabIcon));
        }
    }

    @Override
    public void showLoading(boolean show) {
        swipeContainer.setRefreshing(show);
    }

    @Override
    public void showError(String localizedMessage) {
        Snackbar.make(getView(), localizedMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void removeItem(Item itemTransferred, String fromItemBagId) {
        bagViewMap.get(fromItemBagId).removeItem(itemTransferred);
    }

    @Override
    public void addItem(Item itemTransferred, String toItemBagId) {
        bagViewMap.get(toItemBagId).addItem(itemTransferred);
    }

    @Override
    public void updateItem(Item itemUnequipped, String onBagId) {
        bagViewMap.get(onBagId).updateItem(itemUnequipped);
    }

    @Override
    public void replaceItems(ItemBag itemBagUpdated) {
        bagViewMap.get(itemBagUpdated.withId()).replaceAll(new ArrayList<>(itemBagUpdated.orderedItems()));
    }

    /**
     * Toggle the selection state of an item.
     * <p>
     * If the item was the last one in the selection and is unselected, the selection is stopped.
     * Note that the selection must already be started (actionMode must not be null).
     *
     * @param position Position of the item to toggle the selection state
     */
    private void toggleSelection(SelectableAdapter adapter, int position) {
        adapter.toggleSelection(position);
        int count = 0;
        for (ItemBagView instance : bagViewMap.values()) {
            count += instance.getSelectedItemCount();
        }
        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setSubtitle(count + " selected items");
            actionMode.invalidate();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_filter, null);
        builder.setView(dialogView);
        final Spinner superTypes = (Spinner) dialogView.findViewById(R.id.filter_sTypes);
        final Spinner types = (Spinner) dialogView.findViewById(R.id.filter_sSubtypes);
        final Spinner damageTypes = (Spinner) dialogView.findViewById(R.id.filter_sDmgTypes);
        final CheckBox maxedOnly = (CheckBox) dialogView.findViewById(R.id.filter_cbMaxed);

        superTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        types.setAdapter(null);
                        damageTypes.setAdapter(null);
                        break;
                    case 1:
                        types.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.weaponsSubtypes, android.R.layout.simple_spinner_dropdown_item));
                        damageTypes.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.damageTypes, android.R.layout.simple_spinner_dropdown_item));
                        break;
                    case 2:
                        types.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.armorSubtypes, android.R.layout.simple_spinner_dropdown_item));
                        damageTypes.setAdapter(null);
                        break;
                    case 3:
                        types.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.generalSubtypes, android.R.layout.simple_spinner_dropdown_item));
                        damageTypes.setAdapter(null);
                        break;
                    default:
                        types.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item));
                        damageTypes.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ItemBagView.FilterCommand command = new ItemBagView.FilterCommand();
                        if (superTypes.getSelectedItemPosition() != 0) {
                            command.superType = (String) superTypes.getSelectedItem();
                        }
                        if (types.getSelectedItemPosition() != AdapterView.INVALID_POSITION && types.getSelectedItemPosition() != 0) {
                            command.type = (String) types.getSelectedItem();
                        }
                        if (damageTypes.getSelectedItemPosition() != AdapterView.INVALID_POSITION && damageTypes.getSelectedItemPosition() != 0) {
                            command.damageType = (String) damageTypes.getSelectedItem();
                        }
                        command.maxedOnly = maxedOnly.isChecked();

                        for (ItemBagView adapter : bagViewMap.values()) {
                            adapter.filter(command);
                        }
                    }
                }

        );

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.create().show();
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_inventory, menu);
    }

    private class SendModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_inventory_overlay, menu);
            mode.setTitle("Send ");
            actionMode = mode;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_send:
                    List<Item> toTransfer = new ArrayList<>();
                    for (ItemBagView instance : bagViewMap.values()) {
                        for (Integer itemPosition : instance.getSelectedItems()) {
                            toTransfer.add(instance.getItem(itemPosition));
                        }
                    }

                    new AlertDialog.Builder(getActivity())
                            .setTitle("Transfer to:")
                            .setAdapter(new ItemBagDestinationAdapter(getContext(), R.layout.view_itembagdestination_row, destinations), (dialog, which) -> {
                                presenter.sendItems(toTransfer, destinations.get(which).itemBagId);
                                mode.finish();
                            })
                            .show();
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            for (ItemBagView instance : bagViewMap.values()) {
                instance.clearSelection();
            }
            actionMode = null;
        }

    }

    private class ItemClickListener implements ItemClickSupport.OnItemClickListener {
        @Override
        public void onItemClicked(RecyclerView recyclerView1, int position1, View v) {
            final Item selectedItem = ((ItemAdapter) recyclerView1.getAdapter()).getItem(position1);
            if (selectedItem != null) {
                if (actionMode != null) {
                    toggleSelection((SelectableAdapter) recyclerView1.getAdapter(), position1);
                } else {
                    View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_item_details, null);
                    TextView title = (TextView) dialogView.findViewById(R.id.tvDTitle);
                    TextView second = (TextView) dialogView.findViewById(R.id.tvDDamage);
                    TextView third = (TextView) dialogView.findViewById(R.id.tvDDType);

                    title.setText(selectedItem.getItemName());

                    switch (selectedItem.getItemType()) {
                        case "Armor":
                            second.setText("Defense: " + selectedItem.getDamage());
                            second.setVisibility(View.VISIBLE);
                            break;
                        case "Weapon":
                            second.setText("Attack: " + selectedItem.getDamage());
                            second.setVisibility(View.VISIBLE);
                            third.setText("Damage Type: ");
                            third.append(selectedItem.getDamageType());
                            third.setVisibility(View.VISIBLE);
                            break;
                    }
                    switch (selectedItem.getTierTypeName()) {
                        case "Common":
                            title.setBackgroundColor(getResources().getColor(R.color.colorCommonItem));
                            break;
                        case "Uncommon":
                            title.setBackgroundColor(getResources().getColor(R.color.colorUncommonItem));
                            break;
                        case "Rare":
                            title.setBackgroundColor(getResources().getColor(R.color.colorRareItem));
                            break;
                        case "Legendary":
                            title.setBackgroundColor(getResources().getColor(R.color.colorLegendaryItem));
                            break;
                        case "Exotic":
                            title.setBackgroundColor(getResources().getColor(R.color.colorExoticItem));
                            break;
                    }
                    new AlertDialog.Builder(getContext())
                            .setView(dialogView)
                            .create()
                            .show();
                }
            }
        }
    }

    private class ItemLongClickListener implements ItemClickSupport.OnItemLongClickListener {
        @Override
        public boolean onItemLongClicked(RecyclerView recyclerView1, int position1, View v) {
            if (actionMode == null) {
                actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(new SendModeCallback());
            }
            toggleSelection((SelectableAdapter) recyclerView1.getAdapter(), position1);
            return true;
        }
    }


}
