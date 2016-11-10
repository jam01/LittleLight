package com.jam01.littlelight.adapter.android.presentation.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jam01.littlelight.R;
import com.jam01.littlelight.domain.activity.Activity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

/**
 * Created by jam01 on 11/9/16.
 */
public class ActivityAdapter extends ArrayAdapter<Activity> {
    private final LayoutInflater inflater;
    private final List<Activity> activities;
    private final Context mContext;


    public ActivityAdapter(Context context, int resource, List<Activity> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        activities = objects;
        mContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Activity toDraw = activities.get(position);
        final View toReturn = inflater.inflate(R.layout.layout_activity, parent, false);
        ((TextView) toReturn.findViewById(R.id.tvActivityName)).setText(toDraw.getName());
        LinearLayout checkboxes = (LinearLayout) toReturn.findViewById(R.id.ll_checkboxes);

        if (toDraw.levels() == null || toDraw.levels().isEmpty()) {
            CheckBox cBox = new CheckBox(mContext);
            cBox.setChecked(toDraw.isCompleted());
            checkboxes.addView(cBox);
        } else {
            for (Map.Entry<String, Boolean> entry : toDraw.levels().entrySet()) {
                CheckBox cBox = new CheckBox(mContext);
                cBox.setChecked(entry.getValue());
                checkboxes.addView(cBox);
            }
        }

        Picasso.with(mContext)
                .load(toDraw.getIconUrl())
                .into((ImageView) toReturn.findViewById(R.id.ivActivityIcon));
        return toReturn;
    }

}
