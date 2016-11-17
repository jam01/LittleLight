package com.jam01.littlelight.domain.inventory;

import com.jam01.littlelight.domain.DomainEvent;

import java.util.Date;

/**
 * Created by jam01 on 9/26/16.
 */
public class InventorySynced implements DomainEvent {
    private final Inventory inventoryUpdated;
    private final Date occuredOn;

    public InventorySynced(Inventory inventoryUpdated) {
        this.inventoryUpdated = inventoryUpdated;
        this.occuredOn = new Date();
    }

    @Override
    public Date occurredOn() {
        return occuredOn;
    }

    public Inventory getInventoryUpdated() {
        return inventoryUpdated;
    }
}
