package com.jam01.littlelight.domain.inventory;

import com.jam01.littlelight.domain.identityaccess.Account;

/**
 * Created by jam01 on 7/25/16.
 */
public interface DestinyInventoryService {
    void synchronizeIventoryFor(Account anAccount, InventoryRepository repository);

    void transferItem(String anItemId, String toBagId, Inventory inventory, Account anAccount);

    boolean equip(String anItemId, Character onCharacter, Account anAccount);

    boolean unequip(String anItemId, Character onCharacter, Account anAccount);
}
