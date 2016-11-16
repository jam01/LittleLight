package com.jam01.littlelight.adapter.android.presentation.inventory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jam01.littlelight.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

/**
 * Created by jam01 on 11/15/16.
 */

public class ItemBagDestinationAdapter extends ArrayAdapter<ItemBagDestination> {
    private final List<ItemBagDestination> accounts;
    private final Context mContext;

    public ItemBagDestinationAdapter(Context context, int resource, List<ItemBagDestination> objects) {
        super(context, resource, objects);
        this.accounts = objects;
        mContext = context;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemBagDestination toDraw = accounts.get(position);
        final View toReturn = LayoutInflater.from(mContext).inflate(R.layout.view_itembagdestination_row, parent, false);
        ((TextView) toReturn.findViewById(R.id.tvItemBagName)).setText(toDraw.name);
        Picasso.with(mContext)
                .load(toDraw.iconPath)
                .placeholder(R.drawable.vault)
                .fit()
                .into((ImageView) toReturn.findViewById(R.id.ivItemBagIcon));
        Picasso.with(mContext)
                .load(toDraw.emblemPath)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        toReturn.setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
        return toReturn;
    }

}