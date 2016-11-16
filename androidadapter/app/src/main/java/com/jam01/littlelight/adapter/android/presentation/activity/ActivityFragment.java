package com.jam01.littlelight.adapter.android.presentation.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jam01.littlelight.R;
import com.jam01.littlelight.adapter.android.LittleLight;
import com.jam01.littlelight.adapter.android.presentation.user.CircleTransform;
import com.jam01.littlelight.adapter.android.presentation.user.UserActivity;
import com.jam01.littlelight.adapter.android.utils.SelectableSectionedRecyclerViewAdapter;
import com.jam01.littlelight.adapter.common.presentation.ActivitiesDPO;
import com.jam01.littlelight.domain.activity.Character;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.legend.Legend;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityFragment extends Fragment implements ActivityPresenter.ActivityView {
    private static final String TAG = "LegendFragment";
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MEMBERSHIP_TYPE = "param1";
    private static final String MEMBERSHIP_ID = "param2";
    private ActivityPresenter presenter;
    private ProgressDialog progressDialog;
    private ViewPager mPager;
    private TabLayout tabs;
    private SwipeRefreshLayout swipeContainer;
    private AccountId accountId;


    public ActivityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param accountId Parameter 1.
     * @return A new instance of fragment BlankFragment.
     */
    public static ActivityFragment newInstance(AccountId accountId) {
        ActivityFragment fragment = new ActivityFragment();
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
            presenter = ((LittleLight) getActivity().getApplication()).getComponent().provideActivityPresenter();
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

        ((UserActivity) getActivity()).getSupportActionBar().setTitle("Activities");

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
    public void onStart() {
        super.onStart();
        presenter.bindView(this);
        presenter.onStart(accountId);
    }

    @Override
    public void onStop() {
        presenter.unbindView();
        super.onStop();
    }

    @Override
    public void renderActivity(final ActivitiesDPO account) {
        Legend legend = account.legend;
        final List<Character> characterList = new ArrayList<>(account.activities.withCharacters());
        mPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return characterList.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                RecyclerView recyclerView = new RecyclerView(getContext());
                final ActivityAdapter testAdapter = new ActivityAdapter(characterList.get(position).sortedActivities(),
                        R.layout.view_item_header_row, R.layout.view_activity_tile, getContext());

                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
                int noOfColumns = (int) (dpWidth / (108 + 15));

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

                container.addView(recyclerView);
                return recyclerView;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return characterList.get(position).characterId();
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

        //Set icons on tabs
        for (int i = 0; i < tabs.getTabCount(); i++) {
            tabs.getTabAt(i).setCustomView(R.layout.view_tablayout_tab);
            Picasso.with(getContext())
                    .load(legend.withId(characterList.get(i).characterId()).emblemPath())
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
}
