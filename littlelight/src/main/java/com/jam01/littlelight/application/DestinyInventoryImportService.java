package com.jam01.littlelight.application;

import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.identityaccess.User;
import com.jam01.littlelight.domain.inventory.InventoryRepository;
import com.jam01.littlelight.domain.inventory.DestinyInventoryService;

/**
 * Created by jam01 on 7/25/16.
 */
public class DestinyInventoryImportService {
    private DestinyInventoryService destinyService;
    private InventoryRepository inventoryRepo;
    private User user;

    public DestinyInventoryImportService(DestinyInventoryService destinyService, InventoryRepository inventoryRepo, User user) {
        this.destinyService = destinyService;
        this.inventoryRepo = inventoryRepo;
        this.user = user;
    }

    public void loadInventoriesFromAccount(AccountId anAccountId) {
        inventoryRepo.removeAll();
        inventoryRepo.add(destinyService.ofAccount(anAccountId, user.credentialsOf(anAccountId)));
    }

    public void maintainInventoryFromAccount(AccountId anAccountId) {
        inventoryRepo.ofAccount(anAccountId).updateFrom(destinyService.ofAccount(anAccountId, user.credentialsOf(anAccountId)));
    }
}