package com.jam01.littlelight.adapter.android.presentation.inventory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by jam01 on 11/14/16.
 */

// See: https://gist.github.com/gabrielemariotti/4c189fb1124df4556058
public abstract class SelectableSectionedRecyclerViewAdapter<T> extends SelectableAdapter<RecyclerView.ViewHolder> {
    static final int SECTION_TYPE = 0;
    static final int ITEM_TYPE = 1;
    static final int SHIFT_UP = -1;
    static final int SHIFT_DOWN = 1;
    private final int headerResource;
    private final int itemResource;
    protected Map<Integer, Object> headers = new TreeMap<>();
    protected List<T> mItems;
    protected Context mContext;
    private boolean mValid = true;

    public SelectableSectionedRecyclerViewAdapter(List<T> items, int headerResource, int itemResource, Context context) {
        this.mItems = items;
        this.headerResource = headerResource;
        this.itemResource = itemResource;
        this.mContext = context;

        populateHeaders(mItems);
    }

    @Override
    public int getItemCount() {
        return (mValid ? mItems.size() + headers.size() : 0);
    }

    @Override
    public int getItemViewType(int position) {
        return isPositionHeader(position) ? SECTION_TYPE : ITEM_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int typeView) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (typeView == SECTION_TYPE) {
            return newHeaderInstance(inflater.inflate(headerResource, parent, false));
        } else {
            return newItemInstance(inflater.inflate(itemResource, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (isPositionHeader(position)) {
            onBindHeaderViewHolder(viewHolder, position);
        } else {
            onBindItemViewHolder(viewHolder, position);
        }
    }

    private boolean isPositionHeader(int position) {
        return headers.containsKey(position);
    }

    protected int itemPositionToViewPosition(int itemPosition) {
        int position = itemPosition;
        for (int headerPosition : headers.keySet()) {
            position++;
            if (getItemType(mItems.get(itemPosition)).equals(headers.get(headerPosition))) {
                break;
            }
        }
        return position;
    }

    protected int viewPositionToItemPosition(int position) {
        int itemPosition = position;
        for (int headerPosition : headers.keySet()) {
            if (headerPosition < position) {
                itemPosition--;
            } else break;
        }
        return itemPosition;
    }

    public T getItem(int position) {
        if (isPositionHeader(position))
            return null;
        return mItems.get(viewPositionToItemPosition(position));
    }

    public void removeItem(T itemToRemove) {
        T toRemove = null;
        int itemToRemovePos = -1;
        // TODO: 10/5/16 Consider using the header positions to optimize the search like in Add Item
        //Find the item by itemId
        for (int i = 0, mItemsSize = mItems.size(); i < mItemsSize; i++) {
            T instance = mItems.get(i);
            if (instance.equals(itemToRemove)) {
                toRemove = instance;
                itemToRemovePos = itemPositionToViewPosition(i);
                break;
            }
        }

        mItems.remove(toRemove);

        // If this item was surrounded by headers then remove it with the previous header that would end up empty
        if (headers.containsKey(itemToRemovePos - 1) && headers.containsKey(itemToRemovePos + 1)) {
            headers.remove(itemToRemovePos - 1);
            shiftHeadersBy(2, itemToRemovePos, SHIFT_UP);
            notifyItemRangeRemoved(itemToRemovePos - 1, 2);
            // Else just remove the item
        } else {
            shiftHeadersBy(1, itemToRemovePos, SHIFT_UP);
            notifyItemRemoved(itemToRemovePos);
        }
    }

    public void addItem(T itemToAdd) {
        //Find if we already have this type of item
        if (headers.containsValue(getItemType(itemToAdd))) {
            //Find the entry for its type
            List<Map.Entry<Integer, Long>> entryList = new ArrayList<Map.Entry<Integer, Long>>((Collection) headers.entrySet());
            for (int i = 0; i < headers.size(); i++) {
                if (entryList.get(i).getValue().equals(getItemType(itemToAdd))) {
                    //If it's the last header we add at the end
                    if (i == headers.size() - 1) {
                        mItems.add(itemToAdd);
                        notifyItemInserted(getItemCount());
                    }
                    //Else we use add it at the location of the next header and shift down
                    else {
                        int itemToAddPos = entryList.get(i + 1).getKey();
                        mItems.add(viewPositionToItemPosition(itemToAddPos), itemToAdd);
                        shiftHeadersBy(1, itemToAddPos - 1, SHIFT_DOWN);
                        notifyItemInserted(itemToAddPos);
                    }
                }
            }
        }
        // Else we make that category at the end
        else {
            headers.put(getItemCount(), getItemType(itemToAdd));
            mItems.add(itemToAdd);
            notifyItemRangeInserted(getItemCount() - 1, 2);
        }
    }

    private void shiftHeadersBy(int headerOffset, int itemPosition, int direction) {
        //Move subsequent headers positions up
        Map<Integer, Object> newState = new TreeMap<>();
        for (TreeMap.Entry headerEntry : headers.entrySet()) {
            if ((int) headerEntry.getKey() > itemPosition)
                newState.put((int) headerEntry.getKey() + (direction * headerOffset), headerEntry.getValue());
            else
                newState.put((int) headerEntry.getKey(), headerEntry.getValue());
        }
        headers = newState;
    }

    public void updateItem(T itemToUpdate) {
        for (int i = 0, mItemsSize = mItems.size(); i < mItemsSize; i++) {
            T instance = mItems.get(i);
            if (instance.equals(itemToUpdate)) {
                mItems.set(i, instance);
                notifyItemChanged(itemPositionToViewPosition(i));
            }
        }
    }

    public void replaceAll(List<T> newItems) {
        mItems.clear();
        headers.clear();
        mItems = newItems;
        populateHeaders(newItems);
        notifyDataSetChanged();
    }

    private void populateHeaders(List<T> newItems) {
        if (!newItems.isEmpty()) {
            headers.put(0, getItemType(newItems.get(0)));
            for (int i = 1; i < mItems.size(); i++) {
                if (!getItemType(mItems.get(i)).equals(getItemType(mItems.get(i - 1)))) {
                    headers.put(i + headers.size(), getItemType(mItems.get(i)));
                }
            }
        }
    }

    protected abstract void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position);

    protected abstract void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position);

    protected abstract Object getItemType(T t);

    public abstract RecyclerView.ViewHolder newHeaderInstance(View headerView);

    public abstract RecyclerView.ViewHolder newItemInstance(View itemView);
}