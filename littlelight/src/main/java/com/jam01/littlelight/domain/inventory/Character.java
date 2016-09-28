package com.jam01.littlelight.domain.inventory;

import com.jam01.littlelight.domain.identityaccess.AccountId;

import java.util.Collection;

/**
 * Created by jam01 on 6/9/16.
 */
public class Character extends ItemBag {
    private final String characterId;

    public Character(String characterId, Collection<Item> items, AccountId accountId, String id) {
        super(items, accountId, id);
        this.characterId = characterId;
    }

    public String characterId() {
        return characterId;
    }

    public void equip(String anItemId) {
        if (!this.containsItem(anItemId)) {
            throw new IllegalArgumentException("Character does not have item: " + anItemId);
        }

        Item toEquip = itemMap.get(anItemId);

        for (Item instance : itemMap.values()) {
            if (instance.getItemType() == toEquip.getItemType()) {
                instance.setEquipped(false);
                toEquip.setEquipped(true);
                break;
            }
        }

    }
}
