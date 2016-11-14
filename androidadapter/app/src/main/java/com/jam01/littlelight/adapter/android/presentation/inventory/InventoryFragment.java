package com.jam01.littlelight.adapter.android.presentation.inventory;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.jam01.littlelight.R;
import com.jam01.littlelight.adapter.android.LittleLight;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.inventory.Character;
import com.jam01.littlelight.domain.inventory.Inventory;
import com.jam01.littlelight.domain.inventory.Item;
import com.jam01.littlelight.domain.inventory.ItemBag;
import com.jam01.littlelight.domain.inventory.Vault;

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
    private ProgressDialog progressDialog;
    private ViewPager mPager;
    private TabLayout tabs;
    private SwipeRefreshLayout swipeContainer;
    private AccountId accountId;
    private ActionMode actionMode = null;
    private Map<String, ItemBagView> itemAdapterMap;


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
        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_inventory, container, false);

        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        tabs = (TabLayout) rootView.findViewById(R.id.tabs);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Little Light");
        progressDialog.setMessage("Searching for Guardians");

        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh(accountId);
            }
        });

        mPager.setOffscreenPageLimit(3);
        tabs.setSelectedTabIndicatorColor(Color.WHITE);
        tabs.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
        tabs.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //See: http://stackoverflow.com/questions/25978462/swiperefreshlayout-viewpager-limit-horizontal-scroll-only
                swipeContainer.setEnabled(state == ViewPager.SCROLL_STATE_IDLE);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
        presenter.bindView(this);
        presenter.onStart(accountId);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.d(TAG, "setUserVisibleHint: ");
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser && actionMode != null) {
            actionMode.finish();
        }
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop: ");
        presenter.unbindView();
        super.onStop();
    }

    @Override
    public void renderInventory(final Inventory inventory) {
        itemAdapterMap = new HashMap<>(inventory.allItemBags().size());
        mPager.setAdapter(new PagerAdapter() {
                              List<ItemBag> bags = new ArrayList<>(inventory.allItemBags());

                              @Override
                              public int getCount() {
                                  return inventory.allItemBags().size();
                              }

                              @Override
                              public Object instantiateItem(ViewGroup container, int position) {
                                  ItemBagView itemBagView = new ItemBagView(bags.get(position), getContext());

                                  ItemClickSupport.addTo(itemBagView)
                                          .setOnItemClickListener(new ItemClickListener())
                                          .setOnItemLongClickListener(new ItemLongClickListener());

                                  itemAdapterMap.put(bags.get(position).withId(), itemBagView);
                                  container.addView(itemBagView);
                                  return itemBagView;
                              }

                              @Override
                              public CharSequence getPageTitle(int position) {
                                  if (bags.get(position) instanceof Character) {
                                      return "Character" + position;
                                  } else if (bags.get(position) instanceof Vault) {
                                      return "Vault";
                                  } else
                                      return super.getPageTitle(position);
                              }

                              @Override
                              public boolean isViewFromObject(View view, Object object) {
                                  return view == object;
                              }

                              @Override
                              public void destroyItem(ViewGroup collection, int position, Object view) {
                                  collection.removeView((View) view);
                              }
                          }
        );
    }

    @Override
    public void renderEmblem(String url) {

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
        itemAdapterMap.get(fromItemBagId).removeItem(itemTransferred);
    }

    @Override
    public void addItem(Item itemTransferred, String toItemBagId) {
        itemAdapterMap.get(toItemBagId).addItem(itemTransferred);
    }

    @Override
    public void updateItem(Item itemUnequipped, String onBagId) {
        itemAdapterMap.get(onBagId).updateItem(itemUnequipped);
    }

    @Override
    public void replaceItems(ItemBag itemBagUpdated) {
        itemAdapterMap.get(itemBagUpdated.withId()).replaceAll(new ArrayList<>(itemBagUpdated.orderedItems()));
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
        for (ItemBagView instance : itemAdapterMap.values()) {
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

                        for (ItemBagView adapter : itemAdapterMap.values()) {
                            adapter.filter(command);
                        }
                    }
                }

        );

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }

        );
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
                    String[] destinationArray = new String[itemAdapterMap.values().size()];
                    for (int i = 0; i < itemAdapterMap.values().size(); i++) {
                        destinationArray[i] = (String) itemAdapterMap.keySet().toArray()[i];
                    }
                    final List<Item> toTransfer = new ArrayList<>();
                    for (ItemBagView instance : itemAdapterMap.values()) {
                        for (Integer itemPosition : instance.getSelectedItems()) {
                            toTransfer.add(instance.getItem(itemPosition));
                        }
                    }

                    new AlertDialog.Builder(getActivity())
                            .setTitle("Transfer to")
                            .setItems(destinationArray, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    presenter.sendItems(toTransfer, (String) itemAdapterMap.keySet().toArray()[which]);
                                }
                            })
                            .create()
                            .show();
                    mode.finish();
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            for (ItemBagView instance : itemAdapterMap.values()) {
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
                        case "Legendary":
                            title.setBackgroundColor(0xff5a1bff);
                            break;
                        case "Exotic":
                            title.setBackgroundColor(0xffffb200);
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
