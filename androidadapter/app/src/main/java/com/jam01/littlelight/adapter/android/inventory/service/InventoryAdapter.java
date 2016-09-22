//package com.jam01.littlelight.adapter.android.inventory.service;
//
//import com.bungie.netplatform.destiny.representation.CharacterInventory;
//import com.bungie.netplatform.destiny.representation.Equippable;
//import com.bungie.netplatform.destiny.representation.ItemDefinition;
//import com.bungie.netplatform.destiny.representation.ItemInstance;
//import com.bungie.netplatform.destiny.representation.Vault;
//import com.jam01.littlelight.adapter.android.common.RetrofitDestinyApiFacade;
//import com.jam01.littlelight.domain.identityaccess.CredentialsService;
//import com.jam01.littlelight.domain.identityaccess.AccountCredentials;
//import com.jam01.littlelight.domain.identityaccess.User;
//import com.jam01.littlelight.domain.inventory.ItemBag;
//import com.jam01.littlelight.domain.inventory.Item;
//import com.jam01.littlelight.domain.legend.Legend;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
///**
// * Created by jam01 on 7/23/16.
// */
//public class InventoryAdapter implements DestinyInventoryAdapter {
//    private RetrofitDestinyApiFacade apiFacade = RetrofitDestinyApiFacade.getInstance();
//    private CredentialsService credentialsService;
//    private User user;
//
//    public InventoryAdapter(CredentialsService credentialsService) {
//        this.credentialsService = credentialsService;
//    }
//
//    @Override
//    public Collection<ItemBag> ofLegend(Legend anLegend) {
//        Collection<ItemBag> inventories = new ArrayList<>(anLegend.withCharacterIds().size() + 1);
//        AccountCredentials accountCredentials = credentialsService.ofLegend(anLegend.withId());
//        InventoryTranslator translator = new InventoryTranslator();
//        List<ItemInstance> instances = new ArrayList<>();
//        List<ItemDefinition> definitions;
//
//        for (String characterId : anLegend.withCharacterIds()) {
//            CharacterInventory bungieInventory = apiFacade
//                    .getCharacterInventory((String.valueOf(anLegend.withId().withMembershipType())),
//                            anLegend.withId().withMembershipId(),
//                            characterId,
//                            accountCredentials.asCookieVal(),
//                            accountCredentials.xcsrf());
//
//            for (Equippable equippableList : bungieInventory.getBuckets().getEquippable()) {
//                instances.addAll(equippableList.getItems());
//            }
//
//            for (com.bungie.netplatform.destiny.representation.Item itemList : bungieInventory.getBuckets().getItem()) {
//                instances.addAll(itemList.getItems());
//            }
//
//            definitions = apiFacade.getDefinitionsFor(instances);
//
//            inventories.add(translator.from(characterId, definitions, instances, anLegend.withId()));
//
//            instances.clear();
//            definitions.clear();
//        }
//
//        Vault bungieVault = apiFacade
//                .getVault(String.valueOf(anLegend.withId().withMembershipType()),
//                        accountCredentials.asCookieVal(),
//                        accountCredentials.xcsrf());
//
//        for (com.bungie.netplatform.destiny.representation.Item bungieItems : bungieVault.getBuckets()) {
//            instances.addAll(bungieItems.getItems());
//        }
//
//        definitions = apiFacade.getDefinitionsFor(instances);
//
//        inventories.add(translator.from(definitions, instances, anLegend.withId()));
//
//        instances.clear();
//        definitions.clear();
//
//        return inventories;
//    }
//
//    @Override
//    public boolean take(Item anItem, ItemBag toCharacter) {
//        return false;
//    }
//
//    @Override
//    public boolean store(Item anItem, ItemBag fromCharacter) {
//        return false;
//    }
//
//    @Override
//    public boolean equip(Item anItem, ItemBag onCharacter) {
//        return false;
//    }
//
//}
