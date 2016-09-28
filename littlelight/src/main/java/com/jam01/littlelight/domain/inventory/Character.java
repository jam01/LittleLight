package com.jam01.littlelight.domain.inventory;

import com.jam01.littlelight.domain.DomainEventPublisher;
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

    public void equip(String toEquipId, String toUnequipId) {
        Item toEquip = itemMap.get(toEquipId);
        Item toUnequip = itemMap.get(toUnequipId);

        toEquip.setEquipped(true);
        toUnequip.setEquipped(false);

        DomainEventPublisher.instanceOf().publish(new ItemEquipped(toEquip, toUnequip, ofAccount(), withId()));
    }
}
