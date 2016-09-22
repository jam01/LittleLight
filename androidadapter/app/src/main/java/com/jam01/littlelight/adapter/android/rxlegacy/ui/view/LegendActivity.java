package com.jam01.littlelight.adapter.android.rxlegacy.ui.view;

import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.jam01.littlelight.R;
import com.jam01.littlelight.adapter.android.rxlegacy.ui.model.CharacterLegend;
import com.jam01.littlelight.adapter.android.rxlegacy.ui.presenter.LegendPresenter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LegendActivity extends BaseActivity implements LegendPresenter.LegendView {
    private LegendPresenter presenter;
    private String TAG = getClass().getSimpleName();
    private ProgressDialog progressDialog;
    private ViewPager mPager;
    private TabLayout tabs;
    private LegendPagerAdapter legendPagerAdapter;
    private Target emblemTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_legend, activityContainer);
        mPager = (ViewPager) findViewById(R.id.pager);
        tabs = (TabLayout) findViewById(R.id.tabs);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Little Light");
        progressDialog.setMessage("Searching for Guardians");

        legendPagerAdapter = new LegendPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(legendPagerAdapter);
        mPager.setOffscreenPageLimit(2);
        tabs.setSelectedTabIndicatorColor(Color.WHITE);
        tabs.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
        tabs.setupWithViewPager(mPager);

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                presenter.onLegendSelected(legendPagerAdapter.getLegend(tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (presenter == null)
            presenter = new LegendPresenter();
        presenter.bindView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unbindView();
    }

    @Override
    public void renderLegends(Collection<CharacterLegend> legendsCollection) {
        legendPagerAdapter.setCharacterLegendList((List<CharacterLegend>) legendsCollection);
        legendPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void renderEmblem(String url) {
        if (emblemTarget == null) {
            emblemTarget = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    //This logic divides the emblem bitmap to be drawn on the tabs background and action bar background
                    int tabsHeight = (int) (48 * getApplicationContext().getResources().getDisplayMetrics().density);
                    if (bitmap != null && getSupportActionBar() != null) {
                        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), getSupportActionBar().getHeight() + tabsHeight, false);
                        getSupportActionBar().setBackgroundDrawable(new BitmapDrawable(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight() - tabsHeight)));
                        tabs.setBackgroundDrawable(new BitmapDrawable(Bitmap.createBitmap(bitmap, 0, bitmap.getHeight() - tabsHeight, bitmap.getWidth(), tabsHeight)));
                    }
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
        }
        Picasso.with(getApplicationContext())
                .load(url)
                .into(emblemTarget);
    }

    @Override
    public void showLoading(boolean show) {
        if (show)
            progressDialog.show();
        else
            progressDialog.dismiss();
    }

    private class LegendPagerAdapter extends FragmentPagerAdapter {
        private List<CharacterLegend> characterLegendList = new ArrayList<>();

        public LegendPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void setCharacterLegendList(List<CharacterLegend> characterLegendList) {
            this.characterLegendList = characterLegendList;
        }

        public CharacterLegend getLegend(int position) {
            return characterLegendList.get(position);
        }

        @Override
        public Fragment getItem(int index) {
            CharacterLegendFragment characterLegendFragment = new CharacterLegendFragment();
            Bundle arguments = new Bundle();
            arguments.putString("charId", characterLegendList.get(index).getCharacterId());
            characterLegendFragment.setArguments(arguments);

            return characterLegendFragment;
        }

        @Override
        public int getCount() {
            return characterLegendList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return characterLegendList.get(position).getNickName();
        }
    }
}
