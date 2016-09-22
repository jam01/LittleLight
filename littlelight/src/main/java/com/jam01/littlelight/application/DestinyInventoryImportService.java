package com.jam01.littlelight.application;

import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.identityaccess.User;
import com.jam01.littlelight.domain.inventory.DestinyInventoryService;
import com.jam01.littlelight.domain.inventory.Inventory;
import com.jam01.littlelight.domain.inventory.InventoryRepository;

/**
 * Created by jam01 on 7/25/16.
 */
public class DestinyInventoryImportService {
    private DestinyInventoryService destinyService;
    private InventoryRepository inventoryRepo;
    private User user;

    public DestinyInventoryImportService(DestinyInventoryService destinyService, InventoryRepository inventoryRepo) {
        this.destinyService = destinyService;
        this.inventoryRepo = inventoryRepo;
    }

    public void loadInventoriesFromAccount(AccountId anAccountId) {
        inventoryRepo.removeAll();
        inventoryRepo.addAll(destinyService.allOf(anAccountId, user.credentialsOf(anAccountId)));
    }

    public void maintainInventoryFromAccount(AccountId anAccountId) {
        for (Inventory instance : destinyService.allOf(anAccountId, user.credentialsOf(anAccountId))) {
            if (inventoryRepo.ofId(instance.withId()) == null) {
                inventoryRepo.add(instance);
            } else {
                inventoryRepo.ofId(instance.withId()).updateFrom(instance);
            }
        }
    }
}