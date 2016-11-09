package com.jam01.littlelight.adapter.common.presentation;

import com.jam01.littlelight.domain.inventory.Item;
import com.jam01.littlelight.domain.inventory.ItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jam01 on 11/8/16.
 */
public class ExoticsDPO {
    private final List<Long> exoticItems;
    //    private final List<Item> exoticItems;
    private final List<ItemType> exoticTypes;

    public ExoticsDPO(List<Item> exoticItems, List<ItemType> exoticTypes) {
        this.exoticItems = new ArrayList<>(exoticItems.size());
        for (Item instance : exoticItems) {
            this.exoticItems.add(instance.getBungieItemHash());
        }
        this.exoticTypes = exoticTypes;
    }

    public List<Long> getExoticItems() {
        return exoticItems;
    }

    public List<ItemType> getExoticTypes() {
        return exoticTypes;
    }
}
