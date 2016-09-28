package com.jam01.littlelight.domain.inventory;

import com.jam01.littlelight.domain.DomainEvent;
import com.jam01.littlelight.domain.identityaccess.AccountId;

import java.util.Date;

/**
 * Created by jam01 on 9/26/16.
 */
public class ItemEquipped implements DomainEvent {
    private final Item itemEquipped;
    private final Item itemUnequipped;
    private final AccountId accountId;
    private final String onBagId;
    private final Date occuredOn;

    public ItemEquipped(Item itemEquipped, Item itemUnequipped, AccountId accountId, String onBagId) {
        this.itemEquipped = itemEquipped;
        this.itemUnequipped = itemUnequipped;
        this.accountId = accountId;
        this.onBagId = onBagId;
        this.occuredOn = new Date();
    }

    @Override
    public Date occurredOn() {
        return occuredOn;
    }

    public Item getItemUnequipped() {
        return itemUnequipped;
    }

    public Item getItemEquipped() {
        return itemEquipped;
    }

    public String getOnBagId() {
        return onBagId;
    }

    public AccountId getAccountId() {
        return accountId;
    }
}
