package com.jam01.littlelight.domain.inventory;

import com.jam01.littlelight.domain.DomainEvent;

import java.util.Date;

/**
 * Created by jam01 on 9/26/16.
 */
public class ItemBagUpdated implements DomainEvent {
    private final ItemBag itemBagUpdated;
    private final Date occuredOn;

    public ItemBagUpdated(ItemBag itemBagUpdated) {
        this.itemBagUpdated = itemBagUpdated;
        this.occuredOn = new Date();
    }

    @Override
    public Date occurredOn() {
        return occuredOn;
    }

    public ItemBag getItemBagUpdated() {
        return itemBagUpdated;
    }
}
