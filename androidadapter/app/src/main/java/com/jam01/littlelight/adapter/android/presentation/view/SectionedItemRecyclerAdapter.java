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
public class SectionedItemRecyclerAdapter extends SelectableAdapter<RecyclerView.ViewHolder> {

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
    public int getItemCount() {
        return (mValid ? mItems.size() + headerPositions.size() : 0);
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
            ((HeaderViewHolder) viewHolder).mTextView.setText(Globals.buckets.get(mItems.get(positionToItemPosition(position)).getBucketTypeHash()));
        } else {
            ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
            ImageView icon = itemViewHolder.imageView;
            TextView amount = itemViewHolder.textView;
            CheckBox checkBox = itemViewHolder.checkBox;

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

            // Highlight the item if it's selected
            if (isSelected(position)) {
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

    private int itemPositionToPosition(int itemPosition) {
        int position = itemPosition;
        for (int headerPosition : headerPositions) {
            position++;
            if (mItems.get(positionToItemPosition(headerPosition + 1)).getBucketTypeHash() == mItems.get(itemPosition).getBucketTypeHash()) {
                break;
            }
        }
        return position;
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

    public Item getItem(int position) {
        if (isPositionHeader(position))
            return null;
        return mItems.get(positionToItemPosition(position));
    }

    public void removeItem(Item itemToRemove) {
        Item toRemove = null;
        int itemToRemovePos = -1;

        // TODO: 10/5/16 Consider using the header positions to optimize the search
        //Find the item by itemId
        for (int i = 0, mItemsSize = mItems.size(); i < mItemsSize; i++) {
            Item instance = mItems.get(i);
            if (instance.getItemId().equals(itemToRemove.getItemId())) {
                toRemove = instance;
                itemToRemovePos = itemPositionToPosition(i);
                break;
            }
        }

        mItems.remove(toRemove);

        // If this item was surrounded by headers then remove it with the previous header that would end up empty
        if (headerPositions.contains(itemToRemovePos - 1) && headerPositions.contains(itemToRemovePos + 1)) {
            headerPositions.remove(Integer.valueOf(itemToRemovePos - 1));
            shiftHeadersUpBy(2, itemToRemovePos);
            notifyItemRangeRemoved(itemToRemovePos - 1, 2);
            // Else just remove the item
        } else {
            shiftHeadersUpBy(1, itemToRemovePos);
            notifyItemRemoved(itemToRemovePos);
        }
    }

    public void addItem(Item itemToAdd) {
        int itemToAddPos = -1;

        //Find where to add the item
        for (int i1 = 0, headerPositionsSize = headerPositions.size(); i1 < headerPositionsSize; i1++) {
            int headerPosition = headerPositions.get(i1);
            if (getItem(headerPosition + 1).getBucketTypeHash() == itemToAdd.getBucketTypeHash()) {
                itemToAddPos = headerPosition + 1;
            }
        }
        // This means we found an items from the same category
        if (itemToAddPos != -1) {
            int itemPosition = positionToItemPosition(itemToAddPos);
            mItems.add(itemPosition, itemToAdd);
            shiftHeadersDownBy(1, itemToAddPos);
            notifyItemInserted(itemToAddPos);
            // Else we make that category at the end
        } else {
            headerPositions.add(getItemCount());
            mItems.add(itemToAdd);
            notifyItemRangeInserted(getItemCount() - 1, 2);
        }

//        for (int lastItem = mItems.size() - 1, i = lastItem; i >= 0; i--) {
//            Item instance = mItems.get(i);
//            if (instance.getBucketTypeHash() == (itemToAdd.getBucketTypeHash())) {
//                mItems.add(i + 1, itemToAdd);
//
//
//                int position = itemPositionToPosition(i);
//                //Move subsequent header positions down
//                for (int i1 = 0, headerPositionsSize = headerPositions.size(); i1 < headerPositionsSize; i1++) {
//                    int headerPosition = headerPositions.get(i1);
//                    if (headerPosition > position) {
//                        headerPositions.set(i1, headerPosition + 1);
//                    }
//                }
//
//                notifyItemInserted(position);
//                break;
//            }
//        }

    }

    // TODO: 10/5/16 Make these two methods into one using a direction param
    private void shiftHeadersUpBy(int headerOffset, int itemPosition) {
        //Move subsequent headers positions up
        for (int i1 = 0, headerPositionsSize = headerPositions.size(); i1 < headerPositionsSize; i1++) {
            int headerPosition = headerPositions.get(i1);
            if (headerPosition > itemPosition) {
                headerPositions.set(i1, headerPosition - headerOffset);
            }
        }
    }


    private void shiftHeadersDownBy(int headerOffset, int itemPosition) {
        //Move subsequent headers positions down
        for (int i1 = 0, headerPositionsSize = headerPositions.size(); i1 < headerPositionsSize; i1++) {
            int headerPosition = headerPositions.get(i1);
            if (headerPosition > itemPosition) {
                headerPositions.set(i1, headerPosition + headerOffset);
            }
        }
    }

    public void updateItem(Item itemToUpdate) {
        for (int i = 0, mItemsSize = mItems.size(); i < mItemsSize; i++) {
            Item instance = mItems.get(i);
            if (instance.getItemId().equals(itemToUpdate.getItemId())) {
                mItems.set(i, instance);
                notifyItemChanged(itemPositionToPosition(i));
            }
        }
    }

    public void replaceAll(List<Item> newItems) {
        mItems.clear();
        headerPositions.clear();
        Collections.sort(newItems, new Comparator<Item>() {
            @Override
            public int compare(Item inventoryItem, Item inventoryItem2) {
                return ((Long) inventoryItem.getBucketTypeHash()).compareTo(inventoryItem2.getBucketTypeHash());
            }
        });
        mItems = newItems;
        if (!newItems.isEmpty()) {
            headerPositions.add(0);
            for (int i = 1; i < mItems.size(); i++) {
                if (mItems.get(i).getBucketTypeHash() != mItems.get(i - 1).getBucketTypeHash()) {
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