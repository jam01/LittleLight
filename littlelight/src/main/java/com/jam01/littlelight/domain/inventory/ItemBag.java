package com.jam01.littlelight.domain.inventory;

import com.jam01.littlelight.domain.identityaccess.AccountId;

import java.util.Map;

/**
 * Created by jam01 on 6/9/16.
 */
public abstract class ItemBag {
    private final AccountId accountId;
    private final String id;
    protected Map<String, Item> items;

    public ItemBag(Map<String, Item> items, AccountId accountId, String id) {
        this.items = items;
        this.accountId = accountId;
        this.id = id;
    }


    public String withId() {
        return id;
    }

    public boolean containsItem(String anItemId) {
        return items.containsKey(anItemId);
    }

    protected void updateFrom(ItemBag newState) {
        items.clear();
        items.putAll(newState.items);
    }

    public AccountId ofAccount() {
        return accountId;
    }

    public Item itemOfId(String anItemId) {
        return items.get(anItemId);
    }

    protected void put(Item anItem) {
        items.put(anItem.getItemInstanceId(), anItem);
    }

    protected Item take(String anItemId) {
        Item itemToReturn = items.get(anItemId);
        items.remove(anItemId);
        return itemToReturn;
    }
}
