package com.jam01.littlelight.adapter.common.persistence.legend;

import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.legend.Legend;
import com.jam01.littlelight.domain.legend.LegendRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jam01 on 7/23/16.
 */
public class InMemoryLegendRepository implements LegendRepository {
    private Map<AccountId, Legend> inventoryMap;

    public InMemoryLegendRepository() {
        this.inventoryMap = new HashMap<>();
    }

    @Override
    public Collection<Legend> all() {
        return inventoryMap.values();
    }

    @Override
    public boolean hasOfAccount(AccountId anAccountId) {
        return inventoryMap.containsKey(anAccountId);
    }

    @Override
    public Legend ofId(AccountId anAccountId) {
        return inventoryMap.get(anAccountId);
    }

    @Override
    public void add(Legend inventory) {
        inventoryMap.put(inventory.withId(), inventory);
    }

    @Override
    public void addAll(Collection<Legend> inventories) {
        for (Legend instance : inventories) {
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
