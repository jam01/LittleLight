package com.jam01.littlelight.adapter.android.presentation.view;

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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jam01.littlelight.R;
import com.jam01.littlelight.adapter.android.LittleLight;
import com.jam01.littlelight.adapter.android.presentation.presenter.InventoryPresenter;
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
    private Map<String, SectionedItemRecyclerAdapter> itemAdapterMap;


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
        Log.d(TAG, "newInstance: ");
        InventoryFragment fragment = new InventoryFragment();
        Bundle args = new Bundle();
        args.putInt(MEMBERSHIP_TYPE, accountId.withMembershipType());
        args.putString(MEMBERSHIP_ID, accountId.withMembershipId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
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

                                  RecyclerView recyclerView = new RecyclerView(getContext());
                                  final SectionedItemRecyclerAdapter testAdapter =
                                          new SectionedItemRecyclerAdapter(new ArrayList<>(bags.get(position).items()), getContext());

                                  final int noOfColumns;
                                  {
                                      DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                                      float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
                                      noOfColumns = (int) (dpWidth / 90);
                                  }


                                  GridLayoutManager gridManager = new GridLayoutManager(getContext(), noOfColumns);
                                  gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                                      @Override
                                      public int getSpanSize(int position) {
                                          switch (testAdapter.getItemViewType(position)) {
                                              case SectionedItemRecyclerAdapter.SECTION_TYPE:
                                                  return noOfColumns;
                                              case SectionedItemRecyclerAdapter.ITEM_TYPE:
                                                  return 1;
                                              default:
                                                  return -1;
                                          }
                                      }
                                  });

                                  itemAdapterMap.put(bags.get(position).withId(), testAdapter);
                                  recyclerView.setHasFixedSize(true);
                                  recyclerView.setLayoutManager(gridManager);
                                  recyclerView.setAdapter(testAdapter);

                                  ItemClickSupport.addTo(recyclerView)
                                          .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                                              @Override
                                              public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                                  final Item selectedItem = ((SectionedItemRecyclerAdapter) recyclerView.getAdapter()).getItem(position);
                                                  if (selectedItem != null) {
                                                      if (actionMode != null) {
                                                          toggleSelection((SelectableAdapter) recyclerView.getAdapter(), position);
                                                      } else {
                                                          View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_item_details, null);
                                                          TextView title = (TextView) dialogView.findViewById(R.id.tvDTitle);
                                                          TextView second = (TextView) dialogView.findViewById(R.id.tvDDamage);
                                                          TextView third = (TextView) dialogView.findViewById(R.id.tvDDType);

                                                          title.setText(selectedItem.getItemName());

                                                          switch (selectedItem.getItemType()) {
                                                              case 2:
                                                                  second.setText("Defense: " + selectedItem.getDamage());
                                                                  second.setVisibility(View.VISIBLE);
                                                                  break;
                                                              case 3:
                                                                  second.setText("Attack: " + selectedItem.getDamage());
                                                                  second.setVisibility(View.VISIBLE);
                                                                  third.setText("Damage Type: ");
                                                                  third.append(selectedItem.getDamageType());
                                                                  third.setVisibility(View.VISIBLE);
                                                                  break;
                                                          }

                                                          switch (selectedItem.getTierType()) {
                                                              case 5:
                                                                  title.setBackgroundColor(0xff5a1bff);
                                                                  break;
                                                              case 6:
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
                                          })
                                          .setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                                              @Override
                                              public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                                                  if (actionMode == null) {
                                                      actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(new SendModeCallback());
                                                  }
                                                  toggleSelection((SelectableAdapter) recyclerView.getAdapter(), position);
                                                  return true;
                                              }
                                          });

                                  container.addView(recyclerView);
                                  return recyclerView;
                              }

                              @Override
                              public CharSequence getPageTitle(int position) {
                                  if (inventory.allItemBags().toArray()[position] instanceof Character) {
                                      return "Character" + position;
                                  } else if (inventory.allItemBags().toArray()[position] instanceof Vault) {
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
//        Toast.makeText(getContext(), localizedMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void removeItem(Item itemTransferred, String fromItemBagId) {
        itemAdapterMap.get(fromItemBagId).removeItem(itemTransferred);
    }

    @Override
    public void addItem(Item itemTransferred, String toItemBagId) {
//        itemAdapterMap.get(toItemBagId).addItem(itemTransferred);
    }

    @Override
    public void updateItem(Item itemUnequipped, String onBagId) {
        itemAdapterMap.get(onBagId).updateItem(itemUnequipped);
    }

    @Override
    public void replaceItems(ItemBag itemBagUpdated) {
        itemAdapterMap.get(itemBagUpdated.withId()).replaceAll(new ArrayList<>(itemBagUpdated.items()));
    }

    /**
     * Toggle the selection state of an item.
     * <p/>
     * If the item was the last one in the selection and is unselected, the selection is stopped.
     * Note that the selection must already be started (actionMode must not be null).
     *
     * @param position Position of the item to toggle the selection state
     */
    private void toggleSelection(SelectableAdapter adapter, int position) {
        adapter.toggleSelection(position);
        int count = 0;
        for (SelectableAdapter instance : itemAdapterMap.values()) {
            count += instance.getSelectedItemCount();
        }
        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setSubtitle(count + " selected items");
            actionMode.invalidate();
        }
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
                    for (SectionedItemRecyclerAdapter instance : itemAdapterMap.values()) {
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
            for (SelectableAdapter instance : itemAdapterMap.values()) {
                instance.clearSelection();
            }
            actionMode = null;
        }
    }
}
