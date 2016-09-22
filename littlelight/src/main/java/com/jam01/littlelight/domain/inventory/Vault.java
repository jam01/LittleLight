package com.jam01.littlelight.domain.inventory;

import com.jam01.littlelight.domain.identityaccess.AccountId;

import java.util.Map;

/**
 * Created by jam01 on 6/9/16.
 */
public class Vault extends ItemBag {
    public Vault(String id, Map<String, Item> items, AccountId accountId) {
        super(items, accountId, id);
    }
}
