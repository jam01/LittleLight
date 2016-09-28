package com.jam01.littlelight.domain.inventory;

import com.jam01.littlelight.domain.DomainEventPublisher;
import com.jam01.littlelight.domain.identityaccess.AccountId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jam01 on 9/20/16.
 */
public class Inventory {
    private final AccountId accountId;
    private final List<Character> characters;
    private final Vault vault;

    public Inventory(AccountId accountId, List<Character> characters, Vault vault) {
        this.accountId = accountId;
        this.characters = characters;
        this.vault = vault;
    }

    public List<Character> characters() {
        return characters;
    }

    public AccountId withAccountId() {
        return accountId;
    }

    private List<ItemBag> itemBags() {
        List<ItemBag> toReturn = new ArrayList<>();
        toReturn.addAll(characters);
        toReturn.add(vault);
        return toReturn;
    }

    public boolean containsItemBag(String itemBagId) {
        for (ItemBag instance : itemBags()) {
            if (instance.withId().equals(itemBagId))
                return true;
        }
        return false;
    }

    public ItemBag bagWithId(String anId) {
        for (ItemBag instance : itemBags()) {
            if (instance.withId().equals(anId))
                return instance;
        }
        return null;
    }

    public Collection<ItemBag> allItemBags() {
        return itemBags();
    }

    public void transferItem(String anItemId, String fromBagId, String toBagId) {
        bagWithId(toBagId).put(bagWithId(fromBagId).take(anItemId));
        DomainEventPublisher.instanceOf().publish(new ItemTransferred(bagWithId(toBagId).get(anItemId), accountId, fromBagId, toBagId));
    }

    public void updateFrom(Inventory newState) {
        for (ItemBag instance : itemBags()) {
            instance.updateFrom(newState.bagWithId(instance.withId()));
            DomainEventPublisher.instanceOf().publish(new ItemBagUpdated(instance));
        }
    }

    public ItemBag bagThatContains(String anItemId) {
        for (ItemBag instance : itemBags()) {
            if (instance.containsItem(anItemId)) {
                return instance;
            }
        }
        return null;
    }

    public Vault vault() {
        return vault;
    }
}
