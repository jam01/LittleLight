package com.jam01.littlelight.domain.inventory;

import com.jam01.littlelight.domain.identityaccess.Account;

/**
 * Created by jam01 on 7/25/16.
 */
public interface DestinyInventoryService {
    Inventory ofAccount(Account anAccount);

    void transferItem(String anItemId, String toBagId, Inventory inventory, Account anAccount);

    boolean equip(String anItemId, Character onCharacter, Account anAccount);
}
