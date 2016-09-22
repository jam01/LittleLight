package com.jam01.littlelight.adapter.android.inventory.service;

import com.bungie.netplatform.destiny.representation.ItemDefinition;
import com.bungie.netplatform.destiny.representation.ItemInstance;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.inventory.Character;
import com.jam01.littlelight.domain.inventory.Item;
import com.jam01.littlelight.domain.inventory.Vault;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jam01 on 7/26/16.
 */
public class ItemBagTranslator {
    public Character characterFrom(String characterId, List<ItemDefinition> bungieDefinitions, List<ItemInstance> bungieInstances, AccountId accountId) {
        Map<String, Item> itemMap = new HashMap<>(bungieDefinitions.size());
        for (Item item : transform(bungieDefinitions, bungieInstances)) {
            itemMap.put(item.getItemInstanceId(), item);
        }

        return new Character(characterId, itemMap, accountId, characterId);
    }

    public Vault vaultFrom(List<ItemDefinition> bungieDefinitions, List<ItemInstance> bungieInstances, AccountId accountId) {
        Map<String, Item> itemMap = new HashMap<>(bungieDefinitions.size());
        for (Item item : transform(bungieDefinitions, bungieInstances)) {
            itemMap.put(item.getItemInstanceId(), item);
        }

        return new Vault(accountId.withMembershipType() + accountId.withMembershipId(), itemMap, accountId);
    }

    private Item transform(ItemDefinition definition, ItemInstance instance) {
        Item item = null;

        //We build our item if both params are != null and they represent the same item
        //TODO: consider using illegalArgumentExceptions
        if (instance != null && definition != null && (instance.getItemHash().equals(definition.getItemHash()))) {
            Item.Builder builder = new Item.Builder(
                    instance.getItemInstanceId(),
                    instance.getItemHash(),
                    definition.getItemName(),
                    definition.getBucketTypeHash())
                    .stackSize(instance.getStackSize().intValue())
                    .maxStackSize(definition.getMaxStackSize().intValue())
                    .icon("https://www.bungie.net" + definition.getIcon())
                    .isEquipped(instance.getIsEquipped())
                    .tierType(definition.getTierType().intValue())
                    .isGridComplete(instance.getIsGridComplete())
                    .damageType(instance.getDamageType().intValue())
                    .classType(definition.getClassType().intValue());

            // Some items do not have a primaryStat such as engrams or class items
            if (instance.getPrimaryStat() != null) {
                builder.damage(instance.getPrimaryStat().getValue().intValue())
                        .maxDamage(instance.getPrimaryStat().getMaximumValue().intValue());
            }

            // Setting Materials as itemType 10
            // See http://pastebin.com/Hy5ghZv1 line 552
            if (definition.getBucketTypeHash().equals(3865314626L)) {
                builder.itemType(10);
            } else {
                builder.itemType(definition.getItemType().intValue());
            }

            item = builder.build();
        }
        return item;
    }

    private Collection<Item> transform(List<ItemDefinition> definitions, List<ItemInstance> instances) {
        if (definitions.size() != instances.size()) {
            throw new IllegalArgumentException("Definition and Instance collections are not the same size");
        }

        Collection<Item> items = new ArrayList<>(definitions.size());
        for (int i = 0; i < instances.size(); i++) {
            items.add(transform(definitions.get(i), instances.get(i)));
        }

        return items;
    }

}
