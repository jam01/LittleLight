package com.jam01.littlelight.application;

import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.identityaccess.User;
import com.jam01.littlelight.domain.inventory.DestinyInventoryService;
import com.jam01.littlelight.domain.inventory.Inventory;
import com.jam01.littlelight.domain.inventory.InventoryRepository;

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
            inventoryRepo.add(destinyService.ofAccount(user.ofId(anAccountId)));
        }
        return inventoryRepo.ofAccount(anAccountId);
    }

    public void maintainInventoryOfAccount(AccountId anAccountId) {
        inventoryRepo.ofAccount(anAccountId).updateFrom(destinyService.ofAccount(user.ofId(anAccountId)));
    }
}