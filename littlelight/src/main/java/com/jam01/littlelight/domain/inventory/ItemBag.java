package com.jam01.littlelight.domain.inventory;

import com.jam01.littlelight.domain.identityaccess.AccountId;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jam01 on 6/9/16.
 */
public abstract class ItemBag {
    private final AccountId accountId;
    private final String id;
    protected Map<String, Item> itemMap;

    public ItemBag(Collection<Item> items, AccountId accountId, String id) {
        itemMap = new HashMap<>(items.size());
        for (Item item : items) {
            itemMap.put(item.getItemId(), item);
        }
        this.accountId = accountId;
        this.id = id;
    }


    public String withId() {
        return id;
    }

    public boolean containsItem(String anItemId) {
        return itemMap.containsKey(anItemId);
    }

    protected void updateFrom(ItemBag newState) {
        itemMap.clear();
        itemMap.putAll(newState.itemMap);
    }

    public AccountId ofAccount() {
        return accountId;
    }

    public Item itemOfId(String anItemId) {
        return itemMap.get(anItemId);
    }

    protected void put(Item anItem) {
        itemMap.put(anItem.getItemId(), anItem);
    }

    protected Item take(String anItemId) {
        Item itemToReturn = itemMap.get(anItemId);
        itemMap.remove(anItemId);
        return itemToReturn;
    }

    protected Item get(String anItemId) {
        return itemMap.get(anItemId);
    }

    public Collection<Item> items() {
        return itemMap.values();
    }
}
