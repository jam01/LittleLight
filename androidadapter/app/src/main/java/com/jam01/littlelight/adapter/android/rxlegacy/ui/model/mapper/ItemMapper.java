package com.jam01.littlelight.adapter.android.rxlegacy.ui.model.mapper;

import com.jam01.littlelight.domain.inventory.Item;
import com.jam01.littlelight.adapter.android.rxlegacy.ui.model.InventoryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jam01 on 6/5/16.
 */
public class ItemMapper {
    public static Item transform(InventoryItem inventoryItem) {
        Item item = null;
        if (inventoryItem != null) {
            item = new Item(inventoryItem.getItemInstanceId(),
                    inventoryItem.getItemHash(),
                    inventoryItem.getStackSize(),
                    inventoryItem.getMaxStackSize(),
                    inventoryItem.getIcon(),
                    inventoryItem.getItemName(),
                    inventoryItem.getItemType(),
                    inventoryItem.getIsEquipped(),
                    inventoryItem.getBucketTypeHash(),
                    inventoryItem.getTierType(),
                    inventoryItem.getIsGridComplete(),
                    inventoryItem.getDamageType(),
                    inventoryItem.getDamage(),
                    inventoryItem.getMaxDamage(),
                    inventoryItem.getClassType());
        }
        return item;
    }

    public static List<Item> transform(List<InventoryItem> inventoryItems) {
        List<Item> items = null;
        if (inventoryItems != null) {
            items = new ArrayList<>(inventoryItems.size());
            for (InventoryItem inventoryItem :
                    inventoryItems) {
                items.add(transform(inventoryItem));
            }
        }
        return items;
    }
}
