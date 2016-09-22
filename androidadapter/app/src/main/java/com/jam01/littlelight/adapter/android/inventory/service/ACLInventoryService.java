package com.jam01.littlelight.adapter.android.inventory.service;

import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.inventory.Character;
import com.jam01.littlelight.domain.inventory.DestinyInventoryService;
import com.jam01.littlelight.domain.inventory.Inventory;
import com.jam01.littlelight.domain.inventory.Item;
import com.jam01.littlelight.domain.inventory.Vault;
import com.jam01.littlelight.domain.legend.LegendRepository;

import java.util.Collection;

/**
 * Created by jam01 on 7/25/16.
 */
public class ACLInventoryService implements DestinyInventoryService {
    private DestinyInventoryAdapter destinyAdapter;
    private LegendRepository legendRepo;

    public ACLInventoryService(DestinyInventoryAdapter destinyAdapter, LegendRepository legendRepo) {
        this.destinyAdapter = destinyAdapter;
        this.legendRepo = legendRepo;
    }

    @Override
    public Collection<Inventory> allOf(AccountId aAccountId) {
        return destinyAdapter.ofLegend(legendRepo.ofId(aAccountId));
    }

    @Override
    public Character ofCharacter(String characterId) {
        return null;
    }

    @Override
    public Vault ofAccount(AccountId aAccountId) {
        return null;
    }

    @Override
    public boolean take(Item anItem, Character toCharacter) {
        return destinyAdapter.take(anItem, toCharacter);
    }

    @Override
    public boolean store(Item anItem, Character fromCharacter) {
        return destinyAdapter.store(anItem, fromCharacter);
    }

    @Override
    public boolean equip(Item anItem, Character onCharacter) {
        return destinyAdapter.equip(anItem, onCharacter);
    }
}
