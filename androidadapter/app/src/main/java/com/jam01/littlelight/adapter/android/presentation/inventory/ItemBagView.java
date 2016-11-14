package com.jam01.littlelight.adapter.android.presentation.inventory;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.jam01.littlelight.R;
import com.jam01.littlelight.domain.inventory.Item;
import com.jam01.littlelight.domain.inventory.ItemBag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jam01 on 10/2/16.
 */
public class ItemBagView extends RecyclerView {
    static final int SECTION_TYPE = 0;
    static final int ITEM_TYPE = 1;
    private final String TAG = this.getClass().getSimpleName();
    private ItemBag itemBag;
    private ItemAdapter adapter;

    public ItemBagView(ItemBag bag, Context context) {
        super(context);
        itemBag = bag;
        adapter = new ItemAdapter(itemBag.orderedItems(), R.layout.header, R.layout.layout_inventory_item_cell, context);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 90);

        GridLayoutManager gridManager = new GridLayoutManager(getContext(), noOfColumns);
        gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)) {
                    case ItemBagView.SECTION_TYPE:
                        return noOfColumns;
                    case ItemBagView.ITEM_TYPE:
                        return 1;
                    default:
                        return -1;
                }
            }
        });

        this.setHasFixedSize(true);
        this.setLayoutManager(gridManager);
        this.setAdapter(adapter);
    }

    public void filter(FilterCommand command) {
        List<Item> filteredItems = new ArrayList<>(itemBag.orderedItems());
        if (command.superType != null)
            filteredItems = ItemBag.filterByItemSuperType(filteredItems, command.superType);
        if (command.type != null)
            filteredItems = ItemBag.filterByItemType(filteredItems, command.type);
        if (command.maxedOnly)
            filteredItems = ItemBag.filterByMaxxedOnly(filteredItems);
        if (command.damageType != null)
            filteredItems = ItemBag.filterByDamageType(filteredItems, command.damageType);
        replaceAll(filteredItems);
    }

    public void removeItem(Item itemTransferred) {
        adapter.removeItem(itemTransferred);
    }

    public void addItem(Item itemTransferred) {
        adapter.addItem(itemTransferred);
    }

    public void updateItem(Item itemUnequipped) {
        adapter.updateItem(itemUnequipped);
    }

    public void replaceAll(List<Item> items) {
        adapter.replaceAll(items);
    }

    public List<Integer> getSelectedItems() {
        return adapter.getSelectedItems();
    }

    public Item getItem(int itemPosition) {
        return adapter.getItem(itemPosition);
    }

    public void clearSelection() {
        adapter.clearSelection();
    }

    public int getSelectedItemCount() {
        return adapter.getSelectedItemCount();
    }

    public static class FilterCommand {
        public String superType;
        public String type;
        public boolean maxedOnly;
        public String damageType;

        public FilterCommand() {
        }
    }
}