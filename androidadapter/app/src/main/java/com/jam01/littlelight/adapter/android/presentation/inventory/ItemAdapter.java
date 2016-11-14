package com.jam01.littlelight.adapter.android.presentation.inventory;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jam01.littlelight.R;
import com.jam01.littlelight.domain.inventory.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jam01 on 11/14/16.
 */
public class ItemAdapter extends SelectableSectionedRecyclerViewAdapter<Item> {

    public ItemAdapter(List<Item> items, int headerResource, int itemResource, Context context) {
        super(items, headerResource, itemResource, context);
    }

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        ImageView icon = itemViewHolder.imageView;
        TextView amount = itemViewHolder.textView;
        CheckBox checkBox = itemViewHolder.checkBox;

        Item item = mItems.get(viewPositionToItemPosition(position));
        Picasso.with(mContext)
                .load(item.getIconUrl())
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

    @Override
    protected void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((HeaderViewHolder) viewHolder).mTextView.setText(mItems.get(viewPositionToItemPosition(position)).getItemType());
    }

    @Override
    protected Long getItemType(Item t) {
        return t.getItemTypeHash();
    }

    @Override
    public RecyclerView.ViewHolder newHeaderInstance(View headerView) {
        return new HeaderViewHolder(headerView);
    }

    @Override
    public RecyclerView.ViewHolder newItemInstance(View itemView) {
        return new ItemViewHolder(itemView);
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
