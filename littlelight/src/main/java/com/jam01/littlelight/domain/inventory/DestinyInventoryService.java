package com.jam01.littlelight.domain.inventory;

import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.identityaccess.DestinyCredentials;

/**
 * Created by jam01 on 7/25/16.
 */
public interface DestinyInventoryService {
    AccountInventories ofAccount(AccountId anAccountId);

    void transferItem(String anItemId, String toInventoryId, AccountInventories onAccInventories, DestinyCredentials credentials);

    boolean equip(String anItemId, Character onCharacter, DestinyCredentials credentials);
}
