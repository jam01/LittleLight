package com.jam01.littlelight.adapter.android.inventory.persistence;

import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.inventory.Inventory;
import com.jam01.littlelight.domain.inventory.InventoryRepository;
import com.jam01.littlelight.domain.inventory.Vault;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jam01 on 7/23/16.
 */
public class InMemoryInventoryRepository implements InventoryRepository {
    private Map<String, Inventory> inventoryMap;

    public InMemoryInventoryRepository() {
        this.inventoryMap = new HashMap<>();
    }

    @Override
    public Collection<Inventory> all() {
        return inventoryMap.values();
    }

    @Override
    public Inventory thatContainsItem(String anItemId) {
        for (Inventory instance : inventoryMap.values()) {
            if (instance.containsItem(anItemId)) {
                return instance;
            }
        }
        return null;
    }

    @Override
    public Inventory ofId(String inventoryId) {
        return inventoryMap.get(inventoryId);
    }

    @Override
    public Vault vaultOfLegend(AccountId accountId) {
        return (Vault) inventoryMap.get(accountId.withMembershipType() + accountId.withMembershipId());
    }

    @Override
    public void add(Inventory inventory) {
        if (!inventoryMap.containsKey(inventory.withId())) {
            inventoryMap.put(inventory.withId(), inventory);
        }
    }

    @Override
    public void addAll(Collection<Inventory> inventories) {
        for (Inventory instance : inventories) {
            add(instance);
        }
    }

    @Override
    public void remove(Inventory inventory) {
        inventoryMap.remove(inventory.withId());
    }

    @Override
    public void removeAll() {
        inventoryMap.clear();
    }
}
