package com.jam01.littlelight.application;

import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.identityaccess.User;
import com.jam01.littlelight.domain.inventory.DestinyInventoryService;
import com.jam01.littlelight.domain.inventory.Inventory;
import com.jam01.littlelight.domain.inventory.InventoryRepository;

import java.util.List;

/**
 * Created by jam01 on 7/25/16.
 */
public class InventoryService {
    private DestinyInventoryService destinyService;
    private InventoryRepository inventoryRepo;
    private User user;

    public InventoryService(DestinyInventoryService destinyService, InventoryRepository inventoryRepo, User user) {
        this.destinyService = destinyService;
        this.inventoryRepo = inventoryRepo;
        this.user = user;
    }

    public Inventory ofAccount(AccountId anAccountId) {
        if (!inventoryRepo.hasOfAccount(anAccountId)) {
            synchronizeInventoryOf(anAccountId);
        }
        return inventoryRepo.ofAccount(anAccountId);
    }

    public void synchronizeInventoryOf(AccountId anAccountId) {
        destinyService.synchronizeIventoryFor(user.ofId(anAccountId), inventoryRepo);
    }

    public void transferItem(String anItemId, String toItemBagId) {
        Inventory onInventory = inventoryRepo.thatContains(toItemBagId);
        destinyService.transferItem(anItemId, toItemBagId, onInventory, user.ofId(onInventory.withAccountId()));
    }

    public void transferItems(List<String> itemIds, String toItemBagId) {
        for (String itemId : itemIds) {
            transferItem(itemId, toItemBagId);
        }
    }
}