package com.jam01.littlelight.application;

import com.jam01.littlelight.domain.identityaccess.User;
import com.jam01.littlelight.domain.inventory.AccountInventories;
import com.jam01.littlelight.domain.inventory.AccountInventoriesRepository;
import com.jam01.littlelight.domain.inventory.Character;
import com.jam01.littlelight.domain.inventory.DestinyInventoryService;

/**
 * Created by jam01 on 7/23/16.
 */
public class ItemService {
    private DestinyInventoryService destinyService;
    private User user;
    private AccountInventoriesRepository inventoriesRepo;

    public ItemService(DestinyInventoryService destinyService, User user, AccountInventoriesRepository inventoriesRepo) {
        this.destinyService = destinyService;
        this.user = user;
        this.inventoriesRepo = inventoriesRepo;
    }

    public void transferItem(String anItemId, String toInventoryId) {
        AccountInventories inventories = inventoriesRepo.thatContains(toInventoryId);
        destinyService.transferItem(anItemId, toInventoryId, inventories, user.credentialsOf(inventories.ofId()));
    }

    public void equipItem(String anItemId, String onCharacterId) {
        Character onCharacter = (Character) inventoriesRepo.thatContains(onCharacterId).withId(onCharacterId);
        destinyService.equip(anItemId, onCharacter, user.credentialsOf(onCharacter.ofAccount()));
    }
}
