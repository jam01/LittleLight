package com.jam01.littlelight.adapter.android.presentation.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bungie.netplatform.destiny.representation.Globals;
import com.jam01.littlelight.R;
import com.jam01.littlelight.domain.inventory.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jam01 on 10/2/16.
 */
public class SectionedItemRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static final int SECTION_TYPE = 0;
    static final int ITEM_TYPE = 1;
    private final Context mContext;
    private final String TAG = this.getClass().getSimpleName();
    private boolean mValid = true;
    private LayoutInflater mLayoutInflater;
    private List<Item> mItems;
    private List<Integer> headerPositions = new ArrayList<>();

    public SectionedItemRecyclerAdapter(List<Item> items, Context context) {
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item inventoryItem, Item inventoryItem2) {
                return ((Long) inventoryItem.getBucketTypeHash()).compareTo(inventoryItem2.getBucketTypeHash());
            }
        });
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mItems = items;
        if (!items.isEmpty()) {
            headerPositions.add(0);
            for (int i = 1; i < mItems.size(); i++) {
                if (mItems.get(i).getBucketTypeHash() != mItems.get(i - 1).getBucketTypeHash()) {
                    headerPositions.add(i + headerPositions.size());
                }
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int typeView) {
        if (typeView == SECTION_TYPE) {
            return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.header, parent, false));
        } else {
            return new ItemViewHolder(mLayoutInflater.inflate(R.layout.layout_inventory_item_cell, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (isPositionHeader(position)) {
            ((HeaderViewHolder) viewHolder).mTextView.setText(Globals.buckets.get(mItems.get(positionToItemPosition(position)).getBucketTypeHash()));
        } else {
            ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
            ImageView icon = itemViewHolder.imageView;
            TextView amount = itemViewHolder.textView;

            Item item = mItems.get(positionToItemPosition(position));
            Picasso.with(mContext)
                    .load(item.getIcon())
                    .resize(90, 90)
                    .placeholder(R.mipmap.ic_launcher)
                    .centerCrop()
                    .into(icon);

            if (item.getMaxStackSize() > 1) {
                amount.setText(String.valueOf(item.getStackSize()));
                amount.setTextColor(Color.WHITE);
                amount.setBackgroundColor(0xAA000000);
            } else if (item.isEquipped()) {
                amount.setTextColor(Color.WHITE);
                amount.setText("Equipped");
                amount.setBackgroundColor(0xAA000000);
            } else {
                amount.setText("");
                amount.setBackgroundColor(Color.TRANSPARENT);
            }

            if (item.getGridComplete()) {
                icon.setBackgroundColor(0xffffb500);
                icon.setPadding(10, 10, 10, 10);
            } else {
                icon.setPadding(0, 0, 0, 0);
                icon.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return isPositionHeader(position) ? SECTION_TYPE : ITEM_TYPE;
    }

    private boolean isPositionHeader(int position) {
        return headerPositions.contains(position);
    }

    private int positionToItemPosition(int position) {
        int itemPosition = position;
        for (int headerPosition : headerPositions) {
            if (headerPosition < position) {
                itemPosition--;
            } else break;
        }
        return itemPosition;
    }

    @Override
    public int getItemCount() {
        return (mValid ? mItems.size() + headerPositions.size() : 0);
    }

    public Item getItem(int position) {
        if (isPositionHeader(position))
            return null;
        return mItems.get(positionToItemPosition(position));
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tvHeader);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        CheckBox checkBox;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.ivIcon);
            textView = (TextView) itemView.findViewById(R.id.tvAmount);
            checkBox = (CheckBox) itemView.findViewById(R.id.cbCheck);
        }
    }
}