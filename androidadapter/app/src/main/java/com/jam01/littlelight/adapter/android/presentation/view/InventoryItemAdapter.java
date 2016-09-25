package com.jam01.littlelight.adapter.android.presentation.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bungie.netplatform.destiny.representation.Globals;
import com.jam01.littlelight.R;
import com.jam01.littlelight.domain.inventory.Item;
import com.squareup.picasso.Picasso;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersSimpleAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jam01 on 4/3/15.
 */
public class InventoryItemAdapter extends BaseAdapter implements StickyGridHeadersSimpleAdapter {
    private LayoutInflater inflater;
    private List<Item> items;
    private Context mContext;


    public InventoryItemAdapter(List<Item> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }

    public void addItems(Collection<Item> items) {
        List<Item> itemList = new ArrayList<Item>(items);
        Collections.sort(itemList, new Comparator<Item>() {
            @Override
            public int compare(Item inventoryItem, Item inventoryItem2) {
                return ((Long) inventoryItem.getBucketTypeHash()).compareTo(inventoryItem2.getBucketTypeHash());
            }
        });
        this.items.addAll(itemList);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (inflater == null)
            inflater = (LayoutInflater.from(mContext));

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_inventory_item_cell, parent, false);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.ivIcon);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tvAmount);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.cbCheck);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ImageView icon = viewHolder.imageView;
        TextView amount = viewHolder.textView;

        Item item = (Item) getItem(position);

        Picasso.with(mContext)
                .load(item.getIcon())
                .resize(90, 90)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(icon);

        if (item.getMaxStackSize() > 1) {
            amount.setText(String.valueOf(item.getStackSize()));
            amount.setBackgroundColor(0xAA000000);
        } else if (item.isEquipped()) {
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

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return items.get(position).getBucketTypeHash();
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder mHeaderHolder;
        if (inflater == null)
            inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            mHeaderHolder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.header, parent, false);
            mHeaderHolder.mTextView = (TextView) convertView.findViewById(R.id.tvHeader);
            convertView.setTag(mHeaderHolder);
        } else {
            mHeaderHolder = (HeaderViewHolder) convertView.getTag();
        }

        mHeaderHolder.mTextView.setText(Globals.buckets.get(items.get(position).getBucketTypeHash()));

        return convertView;
    }

    public void clear() {
        this.items.clear();
        this.notifyDataSetChanged();
    }

    static class HeaderViewHolder {
        public TextView mTextView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textView;
        CheckBox checkBox;
    }
}
