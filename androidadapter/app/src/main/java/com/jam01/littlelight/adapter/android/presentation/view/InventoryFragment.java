package com.jam01.littlelight.adapter.android.presentation.view;

import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jam01.littlelight.R;
import com.jam01.littlelight.adapter.android.LittleLight;
import com.jam01.littlelight.adapter.android.presentation.presenter.InventoryPresenter;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.inventory.Character;
import com.jam01.littlelight.domain.inventory.Inventory;
import com.jam01.littlelight.domain.inventory.ItemBag;
import com.jam01.littlelight.domain.inventory.Vault;

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
    private AccountId accountId;


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
    public void onDestroy() {
        presenter.unbindView();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_inventory, container, false);

        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        tabs = (TabLayout) rootView.findViewById(R.id.tabs);
//
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Little Light");
        progressDialog.setMessage("Searching for Guardians");

//        legendPagerAdapter = new LegendPagerAdapter(getSupportFragmentManager());
//        mPager.setAdapter(legendPagerAdapter);

        mPager.setOffscreenPageLimit(3);
        tabs.setSelectedTabIndicatorColor(Color.WHITE);
        tabs.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
        tabs.setupWithViewPager(mPager);

        presenter.bindView(this, accountId);
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void renderLegends(final Inventory inventory) {
        mPager.setAdapter(new PagerAdapter() {
                              @Override
                              public int getCount() {
                                  return inventory.allItemBags().size();
                              }

                              @Override
                              public Object instantiateItem(ViewGroup container, int position) {
//                LayoutInflater inflater = LayoutInflater.from(getContext());
//                ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_bag, container, false);
                                  ItemBagView view = new ItemBagView(getContext(), (ItemBag) inventory.allItemBags().toArray()[position]);
                                  container.addView(view);
                                  return view;
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

//        for (ItemBag bag : inventory.allItemBags()) {
//            for (Item item : bag.items()) {
//                Log.d(TAG, "renderLegends: " + item.getItemName());
//            }
//        }
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
}
