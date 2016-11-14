package com.jam01.littlelight.adapter.android.presentation.inventory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bungie.netplatform.destiny.representation.Globals;
import com.jam01.littlelight.R;
import com.jam01.littlelight.domain.inventory.ItemType;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jam01 on 10/2/16.
 */
public class SectionedItemTypeRecyclerAdapter extends SelectableAdapter<RecyclerView.ViewHolder> {

    static final int SECTION_TYPE = 0;
    static final int ITEM_TYPE = 1;
    private final Context mContext;
    private final String TAG = this.getClass().getSimpleName();
    private boolean mValid = true;
    private LayoutInflater mLayoutInflater;
    private List<ItemType> itemTypes;
    private List<Long> items;
    private List<Integer> headerPositions = new ArrayList<>();

    public SectionedItemTypeRecyclerAdapter(List<ItemType> types, List<Long> items, Context context) {
        Collections.sort(types, new Comparator<ItemType>() {
            @Override
            public int compare(ItemType inventoryItem, ItemType inventoryItem2) {
                return ((Long) inventoryItem.getTypeHash()).compareTo(inventoryItem2.getTypeHash());
            }
        });
        this.items = items;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        itemTypes = types;
        if (!itemTypes.isEmpty()) {
            headerPositions.add(0);
            for (int i = 1; i < itemTypes.size(); i++) {
                if (itemTypes.get(i).getTypeHash() != itemTypes.get(i - 1).getTypeHash()) {
                    headerPositions.add(i + headerPositions.size());
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return (mValid ? itemTypes.size() + headerPositions.size() : 0);
    }

    @Override
    public int getItemViewType(int position) {
        return isPositionHeader(position) ? SECTION_TYPE : ITEM_TYPE;
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
            ((HeaderViewHolder) viewHolder).mTextView.setText(Globals.buckets.get(itemTypes.get(positionToItemPosition(position)).getTypeHash()));
        } else {
            ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
            ImageView icon = itemViewHolder.imageView;
            CheckBox checkBox = itemViewHolder.checkBox;

            ItemType item = itemTypes.get(positionToItemPosition(position));
            Picasso.with(mContext)
                    .load(item.getIconPath())
                    .resize(90, 90)
                    .placeholder(R.mipmap.ic_launcher)
                    .centerCrop()
                    .into(icon);

            // Highlight the item if it's selected
            if (items.contains(item.getBungieItemHash())) {
                checkBox.setVisibility(View.VISIBLE);
                checkBox.setChecked(true);
            } else {
                checkBox.setVisibility(View.INVISIBLE);
            }
        }
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

    public ItemType getItem(int position) {
        if (isPositionHeader(position))
            return null;
        return itemTypes.get(positionToItemPosition(position));
    }


    public void replaceAll(List<ItemType> newItems) {
        itemTypes.clear();
        headerPositions.clear();
        Collections.sort(newItems, new Comparator<ItemType>() {
            @Override
            public int compare(ItemType inventoryItem, ItemType inventoryItem2) {
                return ((Long) inventoryItem.getTypeHash()).compareTo(inventoryItem2.getTypeHash());
            }
        });
        itemTypes = newItems;
        if (!newItems.isEmpty()) {
            headerPositions.add(0);
            for (int i = 1; i < itemTypes.size(); i++) {
                if (itemTypes.get(i).getTypeHash() != itemTypes.get(i - 1).getTypeHash()) {
                    headerPositions.add(i + headerPositions.size());
                }
            }
        }
        notifyDataSetChanged();
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