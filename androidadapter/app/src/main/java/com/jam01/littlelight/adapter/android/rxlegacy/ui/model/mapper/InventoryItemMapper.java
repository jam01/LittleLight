package com.jam01.littlelight.adapter.android.rxlegacy.ui.model.mapper;

import com.jam01.littlelight.domain.inventory.Item;
import com.jam01.littlelight.adapter.android.rxlegacy.ui.model.InventoryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jam01 on 5/11/16.
 */
public class InventoryItemMapper {
    public static InventoryItem transform(Item item) {
        InventoryItem inventoryItem = null;
        if (item != null) {
            inventoryItem = new InventoryItem(item.getItemInstanceId(),
                    item.getItemHash(),
                    item.getStackSize(),
                    item.getMaxStackSize(),
                    item.getIcon(),
                    item.getItemName(),
                    item.getItemType(),
                    item.getEquipped(),
                    item.getBucketTypeHash(),
                    item.getTierType(),
                    item.getGridComplete(),
                    item.getDamageType(),
                    item.getDamage(),
                    item.getMaxDamage(),
                    item.getClassType());
        }
        return inventoryItem;
    }

    public static List<InventoryItem> transform(List<Item> items) {
        List<InventoryItem> inventoryList = null;
        if (items != null) {
            inventoryList = new ArrayList<>();
            for (Item item : items) {
                inventoryList.add(transform(item));
            }
        }
        return inventoryList;
    }

}
