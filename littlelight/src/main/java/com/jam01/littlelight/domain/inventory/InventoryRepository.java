package com.jam01.littlelight.domain.inventory;

import com.jam01.littlelight.domain.identityaccess.AccountId;

import java.util.Collection;

/**
 * Created by jam01 on 6/9/16.
 */
public interface InventoryRepository {
    Collection<Inventory> all();

    Vault vaultOfLegend(AccountId alegendId);

    Inventory thatContainsItem(String anItem);

    Inventory ofId(String inventoryId);

    void add(Inventory inventory);

    void addAll(Collection<Inventory> inventories);

    void remove(Inventory inventory);

    void removeAll();
}
