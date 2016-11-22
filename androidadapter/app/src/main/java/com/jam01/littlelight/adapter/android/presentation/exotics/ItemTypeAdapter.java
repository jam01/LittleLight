package com.jam01.littlelight.adapter.android.presentation.exotics;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jam01.littlelight.R;
import com.jam01.littlelight.adapter.android.utils.SelectableSectionedRecyclerViewAdapter;
import com.jam01.littlelight.domain.inventory.ItemType;
import com.squareup.picasso.MemoryPolicy;
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
        ((HeaderViewHolder) viewHolder).mTextView.setText(mItems.get(viewPositionToItemPosition(position)).getItemType());
    }

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        ImageView icon = itemViewHolder.imageView;
        CheckBox checkBox = itemViewHolder.checkBox;
        TextView header = itemViewHolder.textView;
        View tile = itemViewHolder.tile;
        TextView itemName = itemViewHolder.tvFirstLine;
        TextView subType = itemViewHolder.tvSecondLine;

        ItemType type = mItems.get(viewPositionToItemPosition(position));
        Picasso.with(mContext)
                .load(type.getIconPath())
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .into(icon);


        itemName.setText(type.getItemName());
        subType.setText(type.getItemSubType());

        header.setVisibility(View.GONE);

        // Highlight the type if it's the items list
        if (mItemsPossessed.contains(type.getBungieItemHash())) {
            checkBox.setVisibility(View.VISIBLE);
            checkBox.setChecked(true);
        } else {
            checkBox.setVisibility(View.INVISIBLE);
        }

        switch (type.getTierTypeName()) {
            case "Common":
                tile.setBackgroundColor(mContext.getResources().getColor(R.color.colorCommonItem));
                break;
            case "Uncommon":
                tile.setBackgroundColor(mContext.getResources().getColor(R.color.colorUncommonItem));
                break;
            case "Rare":
                tile.setBackgroundColor(mContext.getResources().getColor(R.color.colorRareItem));
                break;
            case "Legendary":
                tile.setBackgroundColor(mContext.getResources().getColor(R.color.colorLegendaryItem));
                break;
            case "Exotic":
                tile.setBackgroundColor(mContext.getResources().getColor(R.color.colorExoticItem));
                break;
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
        View tile;
        TextView tvFirstLine;
        TextView tvSecondLine;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tile = itemView;
            tvFirstLine = (TextView) itemView.findViewById(R.id.tvItemTileFirstLine);
            tvSecondLine = (TextView) itemView.findViewById(R.id.tvItemTileSecondLine);
            imageView = (ImageView) itemView.findViewById(R.id.ivIcon);
            textView = (TextView) itemView.findViewById(R.id.tvAmount);
            checkBox = (CheckBox) itemView.findViewById(R.id.cbCheck);
        }
    }
}
