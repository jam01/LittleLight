package com.jam01.littlelight.domain.inventory;

import com.jam01.littlelight.domain.DomainEvent;

import java.util.Date;

/**
 * Created by jam01 on 9/26/16.
 */
public class ItemTransferred implements DomainEvent {
    private final Item itemTransferred;
    private final String fromItemBagId;
    private final String toItemBagId;
    private final Date occuredOn;

    public ItemTransferred(Item itemTransferred, String fromItemBagId, String toItemBagId) {
        this.itemTransferred = itemTransferred;
        this.fromItemBagId = fromItemBagId;
        this.toItemBagId = toItemBagId;
        this.occuredOn = new Date();
    }

    @Override
    public Date occurredOn() {
        return occuredOn;
    }

    public Item getItemTransferred() {
        return itemTransferred;
    }

    public String getFromItemBagId() {
        return fromItemBagId;
    }

    public String getToItemBagId() {
        return toItemBagId;
    }
}
