package com.jam01.littlelight.domain.inventory;

import com.jam01.littlelight.domain.identityaccess.AccountId;

import java.util.Collection;

/**
 * Created by jam01 on 9/21/16.
 */
public interface InventoryRepository {
    Collection<Inventory> all();

    boolean hasOfAccount(AccountId anAccountId);

    Inventory thatContains(String aBagId);

    Inventory ofAccount(AccountId anAccountId);

    void add(Inventory Inventory);

    void addAll(Collection<Inventory> inventories);

    void remove(AccountId anAccountId);

    void removeAll();
}
