package com.jam01.littlelight.adapter.android.presentation.inventory;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jam01 on 11/15/16.
 */
class ItemBagViewPagerAdapter extends PagerAdapter {
    private List<ItemBagView> bagViews;

    public ItemBagViewPagerAdapter(List<ItemBagView> bagViews) {
        this.bagViews = bagViews;
    }

    public ItemBagViewPagerAdapter() {
        this.bagViews = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return bagViews.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ItemBagView itemBagView = bagViews.get(position);
        container.addView(itemBagView);
        return itemBagView;
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
