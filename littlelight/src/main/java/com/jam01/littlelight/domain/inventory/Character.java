package com.jam01.littlelight.domain.inventory;

import com.jam01.littlelight.domain.identityaccess.AccountId;

import java.util.Map;

/**
 * Created by jam01 on 6/9/16.
 */
public class Character extends Inventory {
    private final String characterId;

    public Character(Map<String, Item> items, AccountId accountId, String characterId, String id) {
        super(items, accountId, id);
        this.characterId = characterId;
    }

    public String characterId() {
        return characterId;
    }

    protected void equip(String anItemId) {
        if (!this.containsItem(anItemId)) {
            throw new IllegalArgumentException("Character does not have item: " + anItemId);
        }

        Item toEquip = items.get(anItemId);

        for (Item instance : items.values()) {
            if (instance.getItemType() == toEquip.getItemType()) {
                instance.setEquipped(false);
                toEquip.setEquipped(true);
            }
        }
    }
}
