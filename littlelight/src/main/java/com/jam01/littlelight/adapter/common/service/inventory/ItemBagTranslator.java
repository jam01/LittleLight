package com.jam01.littlelight.adapter.common.service.inventory;

import com.bungie.netplatform.destiny.representation.Globals;
import com.bungie.netplatform.destiny.representation.ItemDefinition;
import com.bungie.netplatform.destiny.representation.ItemInstance;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.inventory.Character;
import com.jam01.littlelight.domain.inventory.Item;
import com.jam01.littlelight.domain.inventory.ItemType;
import com.jam01.littlelight.domain.inventory.Vault;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jam01 on 7/26/16.
 */
public class ItemBagTranslator {
    public Character characterFrom(String characterId, List<ItemDefinition> bungieDefinitions, List<ItemInstance> bungieInstances, AccountId accountId) {
        List<Item> items = new ArrayList<>(bungieDefinitions.size());
        items.addAll(transform(bungieDefinitions, bungieInstances));
        return new Character(characterId, items, accountId, characterId);
    }

    public Vault vaultFrom(List<ItemDefinition> bungieDefinitions, List<ItemInstance> bungieInstances, AccountId accountId) {
        List<Item> items = new ArrayList<>(bungieDefinitions.size());
        items.addAll(transform(bungieDefinitions, bungieInstances));
        return new Vault(accountId.withMembershipType() + accountId.withMembershipId(), items, accountId);
    }

    private Item transform(ItemDefinition definition, ItemInstance instance) {
        Item item = null;

        //We build our item if both params are != null and they represent the same item
        //TODO: consider using illegalArgumentExceptions
        if (instance != null && definition != null && (instance.getItemHash().equals(definition.getItemHash()))) {
            Item.Builder builder = new Item.Builder(
                    instance.getItemInstanceId() + "-" + instance.getItemHash(),
                    instance.getItemInstanceId(),
                    transform(definition))
                    .stackSize(instance.getStackSize().intValue())
                    .isEquipped(instance.getIsEquipped())
                    .isGridComplete(instance.getIsGridComplete())
                    .damageType(Globals.damageTypes.get(instance.getDamageType().intValue()));

            // Some items do not have a primaryStat such as engrams or class items
            if (instance.getPrimaryStat() != null) {
                builder.damage(instance.getPrimaryStat().getValue().intValue())
                        .maxDamage(instance.getPrimaryStat().getMaximumValue().intValue());
            }

            item = builder.build();
        } else {
            System.out.println("Something went wrong!");
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

    public ItemType transform(ItemDefinition definition) {
        String itemSuperType;
        // TODO: 11/13/16 Pretty this up
        long i = definition.getBucketTypeHash();
        if (i == 1498876634L || i == 2465295065L || i == 953998645L) {
            itemSuperType = "Weapons";
        } else if (i == 3448274439L || i == 3551918588L || i == 14239492L || i == 20886954L || i == 1585787867L || i == 4023194814L || i == 434908299L) {
            itemSuperType = "Armor";
        } else if (i == 2197472680L || i == 375726501L) {
            itemSuperType = "Progress";
        } else {
            itemSuperType = "General";
//            case 3865314626L://Materials");
//            case 1469714392L://Consumables");
//            case 3284755031L://Subclass");
//            case 2025709351L://Vehicles");
//            case 284967655L://Ships");
//            case 2973005342L://Shaders");
//            case 4274335291L://Emblems");
//            case 3054419239L://Emotes");
//            case 3796357825L://Sparrow Horns");

        }
        return new ItemType(definition.getItemHash(), definition.getItemName(), definition.getBucketTypeHash(),
                definition.getMaxStackSize().intValue(), "https://www.bungie.net" + definition.getIcon(),
                // TODO: 11/19/16 figure out what items are coming up without a tier type name
                definition.getTierTypeName() != null ? definition.getTierTypeName() : "Unknown",
                Globals.classTypes.get(definition.getClassType()), definition.getEquippable(), Globals.buckets.get(definition.getBucketTypeHash()),
                definition.getItemTypeName(), itemSuperType);
    }

    public Collection<ItemType> transform(List<ItemDefinition> definitions) {
        List<ItemType> list = new ArrayList<>(definitions.size());
        for (ItemDefinition instance : definitions) {
            list.add(transform(instance));
        }
        return list;
    }

}
