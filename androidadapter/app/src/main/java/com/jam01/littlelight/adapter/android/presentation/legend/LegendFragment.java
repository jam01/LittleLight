package com.jam01.littlelight.adapter.android.presentation.legend;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jam01.littlelight.R;
import com.jam01.littlelight.adapter.android.LittleLight;
import com.jam01.littlelight.adapter.android.presentation.user.CircleTransform;
import com.jam01.littlelight.adapter.android.presentation.user.UserActivity;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.legend.Character;
import com.jam01.littlelight.domain.legend.Legend;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LegendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LegendFragment extends Fragment implements LegendPresenter.LegendView {
    private static final String TAG = "LegendFragment";
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MEMBERSHIP_TYPE = "param1";
    private static final String MEMBERSHIP_ID = "param2";
    private LegendPresenter presenter;
    private ViewPager mPager;
    private TabLayout tabs;
    private SwipeRefreshLayout swipeContainer;
    private AccountId accountId;


    public LegendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param accountId Parameter 1.
     * @return A new instance of fragment BlankFragment.
     */
    public static LegendFragment newInstance(AccountId accountId) {
        LegendFragment fragment = new LegendFragment();
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
            presenter = ((LittleLight) getActivity().getApplication()).getComponent().provideLegendPresenter();
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
        ((UserActivity) getActivity()).getSupportActionBar().setTitle("Legend");


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
    public void renderLegend(final Legend legend) {
        //Set icons on tabs
        List<Character> characters = new ArrayList<>(legend.withCharacters());
        mPager.setAdapter(new PagerAdapter() {
                              @Override
                              public int getCount() {
                                  return characters.size();
                              }

                              @Override
                              public Object instantiateItem(ViewGroup container, int position) {
                                  View rootView = LayoutInflater.from(getContext()).inflate(R.layout.view_legend, container, false);
                                  Character characterLegend = characters.get(position);

                                  TextView light = (TextView) rootView.findViewById(R.id.inLegend_tvLight);
                                  TextView level = (TextView) rootView.findViewById(R.id.inLegend_tvLevel);
                                  TextView intellect = (TextView) rootView.findViewById(R.id.inLegend_tvIntellect);
                                  TextView discipline = (TextView) rootView.findViewById(R.id.inLegend_tvDiscipline);
                                  TextView strength = (TextView) rootView.findViewById(R.id.inLegend_tvStrength);
                                  ProgressBar armor = (ProgressBar) rootView.findViewById(R.id.inLegend_pbArmor);
                                  ProgressBar recovery = (ProgressBar) rootView.findViewById(R.id.inLegend_pbRecovery);
                                  ProgressBar agility = (ProgressBar) rootView.findViewById(R.id.inLegend_pbAgility);

                                  light.setText(String.valueOf(characterLegend.light()));
                                  level.setText(String.valueOf(characterLegend.level()));
                                  intellect.setText(String.valueOf(characterLegend.intellect()));
                                  discipline.setText(String.valueOf(characterLegend.discipline()));
                                  strength.setText(String.valueOf(characterLegend.strength()));
                                  armor.setProgress(characterLegend.armor());
                                  recovery.setProgress(characterLegend.recovery());
                                  agility.setProgress(characterLegend.agility());

                                  container.addView(rootView);
                                  return rootView;
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

        for (int i = 0; i < tabs.getTabCount(); i++) {
            tabs.getTabAt(i).setCustomView(R.layout.view_tablayout_tab);
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
}
