package com.jam01.littlelight.adapter.android.presentation.view;

import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

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
import java.util.List;

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
    private ActionMode sendMode = null;
    private List<Item> toTransfer = null;


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

        presenter.bindView(this, accountId);
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser && sendMode != null) {
            sendMode.finish();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        presenter.unbindView();
        super.onDestroy();
    }


    @Override
    public void renderInventory(final Inventory inventory) {
        mPager.setAdapter(new PagerAdapter() {
                              @Override
                              public int getCount() {
                                  return inventory.allItemBags().size();
                              }

                              @Override
                              public Object instantiateItem(ViewGroup container, int position) {
                                  final ItemBagView bagView = new ItemBagView(getContext(), (ItemBag) inventory.allItemBags().toArray()[position]);
                                  bagView.setOnScrollListener(new AbsListView.OnScrollListener() {
                                      @Override
                                      public void onScrollStateChanged(AbsListView view, int scrollState) {

                                      }

                                      @Override
                                      public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                          //See: http://stackoverflow.com/questions/25270171/scroll-up-does-not-work-with-swiperefreshlayout-in-listview
                                          if (view.getChildAt(0) != null) {
                                              swipeContainer.setEnabled(view.getFirstVisiblePosition() == 0 && view.getChildAt(0).getTop() == 0);
                                          }
                                      }
                                  });
                                  bagView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                                      @Override
                                      public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                                          Item tmp = (Item) bagView.getAdapter().getItem(position);
                                          Log.d("onCheck", "item selected is " + tmp.getItemName());
                                          if (checked) {
                                              Log.d(TAG, "Adding" + tmp.getItemName());
                                              toTransfer.add(tmp);
                                          } else {
                                              Log.d(TAG, "Removing" + tmp.getItemName());
                                              toTransfer.remove(tmp);
                                          }
                                          mode.setSubtitle(toTransfer.size() + " selected items");
                                      }

                                      @Override
                                      public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                                          mode.getMenuInflater().inflate(R.menu.menu_inventory_overlay, menu);
                                          mode.setTitle("Send ");
                                          sendMode = mode;
                                          if (toTransfer == null) {
                                              toTransfer = new ArrayList<>();
                                          }
                                          return true;
                                      }

                                      @Override
                                      public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                                          for (int i = 0; i < bagView.getCount(); i++) {
                                              if (bagView.getChildAt(i) != null && bagView.getChildAt(i).findViewById(R.id.cbCheck) != null)
                                                  bagView.getChildAt(i).findViewById(R.id.cbCheck).setVisibility(View.VISIBLE);
                                          }
                                          return true;
                                      }

                                      @Override
                                      public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                                          switch (item.getItemId()) {
                                              case R.id.menu_send:
                                                  presenter.sendItems(toTransfer, "2305843009217042777");
                                                  mode.finish();
                                          }
                                          Log.d(TAG, "returningTrue");
                                          return true;
                                      }

                                      @Override
                                      public void onDestroyActionMode(ActionMode mode) {
                                          Log.d(TAG, "onDestroyActionMode");
                                          sendMode = null;
                                          bagView.clearChoices();
                                          toTransfer = null;
                                          for (int i = 0; i < bagView.getCount(); i++) {
                                              if (bagView.getChildAt(i) != null && bagView.getChildAt(i).findViewById(R.id.cbCheck) != null)
                                                  bagView.getChildAt(i).findViewById(R.id.cbCheck).setVisibility(View.INVISIBLE);
                                          }
                                      }
                                  });
                                  container.addView(bagView);
                                  return bagView;
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
        if (show)
            progressDialog.show();
        else
            progressDialog.dismiss();
    }

    @Override
    public void showError(String localizedMessage) {
        Toast.makeText(getContext(), localizedMessage, Toast.LENGTH_SHORT).show();
    }
}
