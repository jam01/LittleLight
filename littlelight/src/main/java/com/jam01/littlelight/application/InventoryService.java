package com.jam01.littlelight.application;

import com.jam01.littlelight.domain.DomainEvent;
import com.jam01.littlelight.domain.DomainEventPublisher;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.identityaccess.User;
import com.jam01.littlelight.domain.inventory.Character;
import com.jam01.littlelight.domain.inventory.DestinyInventoryService;
import com.jam01.littlelight.domain.inventory.Inventory;
import com.jam01.littlelight.domain.inventory.InventoryRepository;
import com.jam01.littlelight.domain.inventory.InventorySynced;
import com.jam01.littlelight.domain.inventory.Item;
import com.jam01.littlelight.domain.inventory.ItemBag;
import com.jam01.littlelight.domain.inventory.ItemEquipped;
import com.jam01.littlelight.domain.inventory.ItemTransferred;
import com.jam01.littlelight.domain.inventory.ItemType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;


/**
 * Created by jam01 on 7/25/16.
 */
public class InventoryService {
    private DestinyInventoryService destinyService;
    private InventoryRepository inventoryRepo;
    private User user;

    public InventoryService(DestinyInventoryService destinyService, InventoryRepository inventoryRepo, User user) {
        this.destinyService = destinyService;
        this.inventoryRepo = inventoryRepo;
        this.user = user;
    }

    public Inventory ofAccount(AccountId anAccountId) {
//        if (!inventoryRepo.hasOfAccount(anAccountId)) {
//            synchronizeInventoryOf(anAccountId);
//        }
        return inventoryRepo.ofAccount(anAccountId);
    }

    public void synchronizeInventoryOf(AccountId anAccountId) {
        destinyService.synchronizeInventoryFor(user.ofId(anAccountId), inventoryRepo);
    }

    public void transferItem(String anItemId, String toItemBagId) {
        Inventory onInventory = inventoryRepo.thatContains(toItemBagId);
        destinyService.transferItem(anItemId, toItemBagId, onInventory, user.ofId(onInventory.withAccountId()));
    }

    public void transferItems(List<String> itemIds, String toItemBagId) {
        for (String itemId : itemIds) {
            transferItem(itemId, toItemBagId);
        }
    }

    public void equipItem(String anItemId, String onCharacterId) {
        Character onCharacter = (Character) inventoryRepo.thatContains(onCharacterId).bagWithId(onCharacterId);
        destinyService.equip(anItemId, onCharacter, user.ofId(onCharacter.ofAccount()));
    }

    public List<ItemType> exotics() {
        List<ItemType> toReturn = new ArrayList<>(destinyService.getExoticTypes());
        Collections.sort(toReturn, new Comparator<ItemType>() {
            @Override
            public int compare(ItemType o1, ItemType o2) {
                return ItemBag.ITEM_ORDER.indexOf(o1.getTypeHash()) - ItemBag.ITEM_ORDER.indexOf(o2.getTypeHash());
            }
        });
        return toReturn;
    }

    public List<Item> exoticsOf(AccountId anAccountId) {
        return inventoryRepo.ofAccount(anAccountId).getExotics();
    }

    public Observable<DomainEvent> subscribeToInventoryEvents(final AccountId subscriberAccountId) {
        return DomainEventPublisher.instanceOf().getEvents()
                .filter(new Predicate<DomainEvent>() {
                    @Override
                    public boolean test(DomainEvent domainEvent) throws Exception {
                        if (domainEvent instanceof ItemTransferred)
                            return (((ItemTransferred) domainEvent).onAccountId().equals(subscriberAccountId)
                                    || ((ItemTransferred) domainEvent).onAccountId().equals(subscriberAccountId));
                        if (domainEvent instanceof ItemEquipped)
                            return ((ItemEquipped) domainEvent).getAccountId().equals(subscriberAccountId);
                        if (domainEvent instanceof InventorySynced)
                            return ((InventorySynced) domainEvent).getInventoryUpdated().withAccountId().equals(subscriberAccountId);
                        return false;
                    }
                });
    }
}