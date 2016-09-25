package com.jam01.littlelight.adapter.common.persistence.inventory;

import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.inventory.Inventory;
import com.jam01.littlelight.domain.inventory.InventoryRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jam01 on 7/23/16.
 */
public class InMemoryInventoryRepository implements InventoryRepository {
    private Map<AccountId, Inventory> inventoryMap;

    public InMemoryInventoryRepository() {
        this.inventoryMap = new HashMap<>();
    }

    @Override
    public Collection<Inventory> all() {
        return inventoryMap.values();
    }

    @Override
    public boolean hasOfAccount(AccountId anAccountId) {
        return inventoryMap.containsKey(anAccountId);
    }

    @Override
    public Inventory thatContains(String aBagId) {
        for (Inventory instance : inventoryMap.values()) {
            if (instance.containsItemBag(aBagId)) {
                return instance;
            }
        }
        return null;
    }

    @Override
    public Inventory ofAccount(AccountId anAccountId) {
        return inventoryMap.get(anAccountId);
    }

    @Override
    public void add(Inventory inventory) {
        inventoryMap.put(inventory.withAccountId(), inventory);
    }

    @Override
    public void addAll(Collection<Inventory> inventories) {
        for (Inventory instance : inventories) {
            add(instance);
        }
    }

    @Override
    public void remove(AccountId anAccountId) {
        inventoryMap.remove(anAccountId);
    }

    @Override
    public void removeAll() {
        inventoryMap.clear();
    }
}
