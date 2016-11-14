package com.jam01.littlelight.adapter.android.presentation.inventory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bungie.netplatform.destiny.representation.Globals;
import com.jam01.littlelight.R;
import com.jam01.littlelight.domain.inventory.ItemType;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jam01 on 11/14/16.
 */
public class ItemTypeAdapter extends SelectableSectionedRecyclerViewAdapter<ItemType> {
    private List<Long> mItemsPossessed;

    public ItemTypeAdapter(List<ItemType> itemTypes, List<Long> itemsPossessed, int headerResource, int itemResource, Context context) {
        super(itemTypes, headerResource, itemResource, context);
        this.mItemsPossessed = itemsPossessed;
    }

    @Override
    protected void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((HeaderViewHolder) viewHolder).mTextView.setText(Globals.buckets.get(mItems.get(viewPositionToItemPosition(position)).getTypeHash()));

    }

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        ImageView icon = itemViewHolder.imageView;
        CheckBox checkBox = itemViewHolder.checkBox;

        ItemType type = mItems.get(viewPositionToItemPosition(position));
        Picasso.with(mContext)
                .load(type.getIconPath())
                .resize(90, 90)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(icon);

        // Highlight the type if it's the items list
        if (mItemsPossessed.contains(type.getBungieItemHash())) {
            checkBox.setVisibility(View.VISIBLE);
            checkBox.setChecked(true);
        } else {
            checkBox.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected Object getItemType(ItemType itemType) {
        return itemType.getTypeHash();
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
