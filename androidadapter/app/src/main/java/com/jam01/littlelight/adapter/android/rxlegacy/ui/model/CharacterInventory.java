package com.jam01.littlelight.adapter.android.rxlegacy.ui.model;

import java.util.List;

/**
 * Created by jam01 on 5/4/16.
 */
public class CharacterInventory {
    private String characterId;
    private String nickname;
    private List<InventoryItem> inventoryItems;
    public String getNickname() {
        return nickname;
    }
}
