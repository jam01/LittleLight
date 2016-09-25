package com.jam01.littlelight.application;

import com.jam01.littlelight.domain.identityaccess.User;
import com.jam01.littlelight.domain.inventory.Character;
import com.jam01.littlelight.domain.inventory.DestinyInventoryService;
import com.jam01.littlelight.domain.inventory.Inventory;
import com.jam01.littlelight.domain.inventory.InventoryRepository;
import com.jam01.littlelight.domain.inventory.ItemBag;

/**
 * Created by jam01 on 7/23/16.
 */
public class ItemService {
    private DestinyInventoryService destinyService;
    private User user;
    private InventoryRepository inventoryRepo;

    public ItemService(DestinyInventoryService destinyService, InventoryRepository inventoryRepo, User user) {
        this.destinyService = destinyService;
        this.user = user;
        this.inventoryRepo = inventoryRepo;
    }

    public ItemBag itemBag(String aBagId) {
        return inventoryRepo.thatContains(aBagId).bagWithId(aBagId);
    }

    public void transferItem(String anItemId, String toItemBagId) {
        Inventory onInventory = inventoryRepo.thatContains(toItemBagId);
        destinyService.transferItem(anItemId, toItemBagId, onInventory, user.ofId(onInventory.withAccountId()));
    }

    public void equipItem(String anItemId, String onCharacterId) {
        Character onCharacter = (Character) inventoryRepo.thatContains(onCharacterId).bagWithId(onCharacterId);
        destinyService.equip(anItemId, onCharacter, user.ofId(onCharacter.ofAccount()));
    }
}
