package com.jam01.littlelight.adapter.android.presentation.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jam01.littlelight.R;
import com.jam01.littlelight.adapter.android.utils.SelectableSectionedRecyclerViewAdapter;
import com.jam01.littlelight.domain.activity.Activity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

/**
 * Created by jam01 on 11/16/16.
 */

public class ActivityAdapter extends SelectableSectionedRecyclerViewAdapter<Activity> {

    public ActivityAdapter(List<Activity> items, int headerResource, int itemResource, Context context) {
        super(items, headerResource, itemResource, context);
    }

    @Override
    protected void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((HeaderViewHolder) viewHolder).mTextView.setText(mItems.get(viewPositionToItemPosition(position)).getActivityType());
    }

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        ImageView icon = itemViewHolder.imageView;
        TextView name = itemViewHolder.textView;
        ViewGroup checkboxesLayout = itemViewHolder.checkboxesLayout;

        Activity toDraw = mItems.get(viewPositionToItemPosition(position));
        Picasso.with(mContext)
                .load(toDraw.getIconUrl())
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .into(icon);

        name.setText(toDraw.getName());

        checkboxesLayout.removeAllViews();
        if (toDraw.levels() == null || toDraw.levels().isEmpty()) {
            CheckBox cBox = new CheckBox(mContext);
            cBox.setChecked(toDraw.isCompleted());
            cBox.setClickable(false);
            checkboxesLayout.addView(cBox);
        } else {
            for (Map.Entry<String, Boolean> entry : toDraw.levels().entrySet()) {
                CheckBox cBox = new CheckBox(mContext);
                cBox.setChecked(entry.getValue());
                cBox.setClickable(false);
                checkboxesLayout.addView(cBox);
            }
        }
    }

    @Override
    protected Object getItemType(Activity activity) {
        return activity.getActivityTypeInt();
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
        ViewGroup checkboxesLayout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            checkboxesLayout = (ViewGroup) itemView.findViewById(R.id.ll_checkboxes);
            imageView = (ImageView) itemView.findViewById(R.id.ivActivityIcon);
            textView = (TextView) itemView.findViewById(R.id.tvActivityName);
        }
    }
}
