package com.jam01.littlelight.adapter.android.presentation.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jam01.littlelight.R;
import com.jam01.littlelight.adapter.android.LittleLight;
import com.jam01.littlelight.adapter.android.presentation.presenter.ItemBagPresenter;
import com.jam01.littlelight.domain.inventory.Item;
import com.jam01.littlelight.domain.inventory.ItemBag;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersBaseAdapterWrapper;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersGridView;

import java.util.ArrayList;

/**
 * Created by jam01 on 9/24/16.
 */
public class ItemBagView extends RelativeLayout implements ItemBagPresenter.ItemBagView {
    private ItemBagPresenter presenter;
    private InventoryItemAdapter itemAdapter;

    public ItemBagView(Context context, ItemBag itemBag) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_bag, this, true);

        itemAdapter = new InventoryItemAdapter(new ArrayList<Item>(), context);

        StickyGridHeadersGridView gridView = (StickyGridHeadersGridView) findViewById(R.id.gvCharac);
        gridView.setAdapter(itemAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),
                        ((Item) ((StickyGridHeadersBaseAdapterWrapper) parent.getAdapter()).getWrappedAdapter().getItem(position)).getItemName(),
                        Toast.LENGTH_SHORT).show();
            }
        });


        if (presenter == null) {
            presenter = ((LittleLight) ((Activity) context).getApplication()).getComponent().provideItemBagPresenter();
        }
        presenter.bindView(this);

        renderItems(itemBag);
    }

    @Override
    public void showLoading(boolean bool) {

    }

    @Override
    public void renderItems(ItemBag itemBag) {
        itemAdapter.addItems(itemBag.items());
        itemAdapter.notifyDataSetChanged();
    }

    @Override
    public void clearItems() {
        itemAdapter.clear();
    }
}
