//package com.jam01.littlelight.domain.inventory;
//
///**
// * Created by jam01 on 8/4/16.
// */
//public class InventoryService {
//    InventoryRepository inventoryRepo;
//
//    public InventoryService(InventoryRepository inventoryRepo) {
//        this.inventoryRepo = inventoryRepo;
//    }
//
//    public void transferItem(String anItemId, ItemBag fromItemBag, ItemBag toItemBag) {
//        if (!fromItemBag.ofAccount().equals(toItemBag.ofAccount())) {
//            throw new UnsupportedOperationException("Cannot transfer items between Legends");
//        }
//
//        if (toItemBag instanceof Vault) {
//            ((Character) fromItemBag).store(anItemId, (Vault) toItemBag);
//
//        } else if (fromItemBag instanceof Vault) {
//            ((Character) toItemBag).take(anItemId, (Vault) fromItemBag);
//
//        } else {
//            Vault vault = inventoryRepo.vaultOfLegend(fromItemBag.ofAccount());
//            ((Character) fromItemBag).store(anItemId, vault);
//            ((Character) toItemBag).take(anItemId, vault);
//        }
//    }
//
//    public void equipItem(String anItemId, ItemBag onCharacter) {
//        if (!(onCharacter instanceof Character)) {
//            throw new UnsupportedOperationException("Can only equip items on Characters");
//        }
//
//        if (!onCharacter.containsItem(anItemId)) {
//            ItemBag fromItemBag = inventoryRepo.thatContainsItem(anItemId);
//            this.transferItem(anItemId, fromItemBag, onCharacter);
//        }
//
//        ((Character) onCharacter).equip(anItemId);
//    }
//}
