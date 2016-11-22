package com.jam01.littlelight.adapter.android.presentation.exotics;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jam01.littlelight.R;
import com.jam01.littlelight.adapter.android.LittleLight;
import com.jam01.littlelight.adapter.android.presentation.user.UserActivity;
import com.jam01.littlelight.adapter.android.utils.ItemClickSupport;
import com.jam01.littlelight.adapter.android.utils.SelectableSectionedRecyclerViewAdapter;
import com.jam01.littlelight.adapter.common.presentation.ExoticsDPO;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.inventory.ItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExoticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExoticsFragment extends Fragment implements ExoticsPresenter.ExoticsView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MEMBERSHIP_TYPE = "param1";
    private static final String MEMBERSHIP_ID = "param2";
    private ViewPager mPager;
    private TabLayout tabs;
    private SwipeRefreshLayout swipeContainer;
    private AccountId accountId;
    private ExoticsPresenter presenter;

    public ExoticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param accountId Parameter 1.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExoticsFragment newInstance(AccountId accountId) {
        ExoticsFragment fragment = new ExoticsFragment();
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
            presenter = ((LittleLight) getActivity().getApplication()).getComponent().provideExoticsPresenter();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_generic, container, false);
        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        tabs = new TabLayout(getContext());
        ((UserActivity) getActivity()).getAppBar().addView(tabs);
        ((UserActivity) getActivity()).getSupportActionBar().setTitle("Exotic Items");

        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(() -> presenter.refresh(accountId));

        mPager.setOffscreenPageLimit(3);
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
        tabs.setupWithViewPager(mPager);

        return rootView;
    }

    @Override
    public void renderExotics(final ExoticsDPO exoticsList) {
        final List<List<ItemType>> subLists = new ArrayList<>(4);
        List<ItemType> titanItems = new ArrayList<>();
        List<ItemType> hunterItems = new ArrayList<>();
        List<ItemType> warlockItems = new ArrayList<>();
        List<ItemType> weaponItems = new ArrayList<>();
        subLists.add(titanItems);
        subLists.add(hunterItems);
        subLists.add(warlockItems);
        subLists.add(weaponItems);

        for (ItemType instance :
                exoticsList.exoticTypes) {
            if (instance.getClassType() == null)
                weaponItems.add(instance);
            else
                switch (instance.getClassType()) {
                    case "Titan":
                        titanItems.add(instance);
                        break;
                    case "Hunter":
                        hunterItems.add(instance);
                        break;
                    case "Warlock":
                        warlockItems.add(instance);
                        break;
                }
        }


        mPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                RecyclerView recyclerView = new RecyclerView(getContext());
                final ItemTypeAdapter testAdapter =
                        new ItemTypeAdapter(subLists.get(position), exoticsList.exoticItems, R.layout.view_item_header_row, R.layout.view_item_tile, getContext());

                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
                int noOfColumns = (int) (dpWidth / 108);

                GridLayoutManager gridManager = new GridLayoutManager(getContext(), noOfColumns);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        switch (testAdapter.getItemViewType(position)) {
                            case SelectableSectionedRecyclerViewAdapter.SECTION_TYPE:
                                return noOfColumns;
                            case SelectableSectionedRecyclerViewAdapter.ITEM_TYPE:
                                return 1;
                            default:
                                return -1;
                        }
                    }
                });

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(gridManager);
                recyclerView.setAdapter(testAdapter);

                ItemClickSupport.addTo(recyclerView)
                        .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                            @Override
                            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                final ItemType selectedItem = ((ItemTypeAdapter) recyclerView.getAdapter()).getItem(position);
                                if (selectedItem != null) {
                                    View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_item_details, null);
                                    TextView title = (TextView) dialogView.findViewById(R.id.tvDTitle);
                                    TextView subType = (TextView) dialogView.findViewById(R.id.tvDDSubType);

                                    title.setText(selectedItem.getItemName());
                                    subType.setText(selectedItem.getItemSubType());

                                    int bakgroundColor;
                                    switch (selectedItem.getTierTypeName()) {
                                        case "Common":
                                            bakgroundColor = getResources().getColor(R.color.colorCommonItem);
                                            break;
                                        case "Uncommon":
                                            bakgroundColor = getResources().getColor(R.color.colorUncommonItem);
                                            break;
                                        case "Rare":
                                            bakgroundColor = getResources().getColor(R.color.colorRareItem);
                                            break;
                                        case "Legendary":
                                            bakgroundColor = getResources().getColor(R.color.colorLegendaryItem);
                                            break;
                                        case "Exotic":
                                            bakgroundColor = getResources().getColor(R.color.colorExoticItem);
                                            break;
                                        default:
                                            bakgroundColor = getResources().getColor(R.color.colorCommonItem);
                                            break;
                                    }
                                    dialogView.setBackgroundColor(ColorUtils.compositeColors(getResources().getColor(R.color.colorTranslucid), bakgroundColor));

                                    new AlertDialog.Builder(getContext())
                                            .setView(dialogView)
                                            .create()
                                            .show();
                                }
                            }
                        });

                container.addView(recyclerView);
                return recyclerView;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Titan";
                    case 1:
                        return "Hunter";
                    case 2:
                        return "Warlock";
                    case 3:
                        return "Weapons";
                }
                return "unknown";
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup collection, int position, Object view) {
                collection.removeView((View) view);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.bindView(this);
        presenter.onStart(accountId);
    }

    @Override
    public void onDestroyView() {
        ((UserActivity) getActivity()).getAppBar().removeView(tabs);
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        presenter.unbindView();
        super.onStop();
    }

    @Override
    public void showLoading(boolean show) {
        swipeContainer.setRefreshing(show);
    }

    @Override
    public void showError(String localizedMessage) {
        Snackbar.make(getView(), localizedMessage, Snackbar.LENGTH_LONG).show();
    }
}
