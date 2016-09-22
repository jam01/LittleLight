package com.jam01.littlelight.domain.inventory;

import com.jam01.littlelight.domain.identityaccess.AccountId;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jam01 on 9/20/16.
 */
public class AccountInventories {
    private final AccountId accountId;
    private final Map<String, Inventory> inventories;

    public AccountId ofId() {
        return accountId;
    }

    public AccountInventories(AccountId accountId, List<Inventory> inventories) {
        this.accountId = accountId;
        this.inventories = new HashMap<>(inventories.size());
        for (Inventory instance : inventories) {
            this.inventories.put(instance.withId(), instance);
        }
    }

    protected Inventory thatContainsItem(String anItem) {
        for (Inventory instance : inventories.values()) {
            if (instance.containsItem(anItem))
                return instance;
        }
        return null;
    }

    public Inventory withId(String anId) {
        return inventories.get(anId);
    }

    protected Collection<Inventory> all() {
        return inventories.values();
    }

    protected void transferItem(String anItemId, String fromInvId, String toInvId) {
        inventories.get(toInvId).put(inventories.get(fromInvId).take(anItemId));
    }
}
