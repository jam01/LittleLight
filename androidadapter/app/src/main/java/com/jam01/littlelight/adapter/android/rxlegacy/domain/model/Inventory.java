package com.jam01.littlelight.adapter.android.rxlegacy.domain.model;

import com.jam01.littlelight.domain.inventory.Item;

import java.util.List;

/**
 * Created by jam01 on 5/8/16.
 */
public class Inventory {

    private final List<Item> items;
    private final String characterId;

    Inventory(List<Item> items, String characterId) {
        this.items = items;
        this.characterId = characterId;
    }

    public List<Item> getItems() {
        return items;
    }

    public String getCharacterId() {
        return characterId;
    }
}
