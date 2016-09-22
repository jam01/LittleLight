package com.jam01.littlelight.domain.inventory;

/**
 * Created by jam01 on 8/4/16.
 */
public class InventoryService {
    InventoryRepository inventoryRepo;

    public InventoryService(InventoryRepository inventoryRepo) {
        this.inventoryRepo = inventoryRepo;
    }

    public void transferItem(String anItemId, Inventory fromInventory, Inventory toInventory) {
        if (!fromInventory.ofAccount().equals(toInventory.ofAccount())) {
            throw new UnsupportedOperationException("Cannot transfer items between Legends");
        }

        if (toInventory instanceof Vault) {
            ((Character) fromInventory).store(anItemId, (Vault) toInventory);

        } else if (fromInventory instanceof Vault) {
            ((Character) toInventory).take(anItemId, (Vault) fromInventory);

        } else {
            Vault vault = inventoryRepo.vaultOfLegend(fromInventory.ofAccount());
            ((Character) fromInventory).store(anItemId, vault);
            ((Character) toInventory).take(anItemId, vault);
        }
    }

    public void equipItem(String anItemId, Inventory onCharacter) {
        if (!(onCharacter instanceof Character)) {
            throw new UnsupportedOperationException("Can only equip items on Characters");
        }

        if (!onCharacter.containsItem(anItemId)) {
            Inventory fromInventory = inventoryRepo.thatContainsItem(anItemId);
            this.transferItem(anItemId, fromInventory, onCharacter);
        }

        ((Character) onCharacter).equip(anItemId);
    }
}
