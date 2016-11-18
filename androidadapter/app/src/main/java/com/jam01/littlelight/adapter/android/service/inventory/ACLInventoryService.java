package com.jam01.littlelight.adapter.android.service.inventory;

import com.bungie.netplatform.destiny.api.DestinyApi;
import com.bungie.netplatform.destiny.api.EquipCommand;
import com.bungie.netplatform.destiny.api.TransferCommand;
import com.bungie.netplatform.destiny.representation.BungieResponse;
import com.bungie.netplatform.destiny.representation.CharacterInventory;
import com.bungie.netplatform.destiny.representation.DataResponse;
import com.bungie.netplatform.destiny.representation.Equippable;
import com.bungie.netplatform.destiny.representation.ItemDefinition;
import com.bungie.netplatform.destiny.representation.ItemInstance;
import com.jam01.littlelight.adapter.common.service.BungieResponseValidator;
import com.jam01.littlelight.adapter.common.service.inventory.ItemBagTranslator;
import com.jam01.littlelight.adapter.common.service.inventory.LocalDefinitionsDbService;
import com.jam01.littlelight.domain.DomainEventPublisher;
import com.jam01.littlelight.domain.identityaccess.Account;
import com.jam01.littlelight.domain.identityaccess.AccountCredentials;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.inventory.Character;
import com.jam01.littlelight.domain.inventory.DestinyInventoryService;
import com.jam01.littlelight.domain.inventory.Inventory;
import com.jam01.littlelight.domain.inventory.InventoryRepository;
import com.jam01.littlelight.domain.inventory.InventorySynced;
import com.jam01.littlelight.domain.inventory.Item;
import com.jam01.littlelight.domain.inventory.ItemBag;
import com.jam01.littlelight.domain.inventory.ItemType;
import com.jam01.littlelight.domain.inventory.Vault;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jam01 on 7/25/16.
 */
public class ACLInventoryService implements DestinyInventoryService {
    private final DestinyApi destinyApi;
    private final LocalDefinitionsDbService definitionsService;
    private final String TAG = this.getClass().getSimpleName();

    public ACLInventoryService(DestinyApi destinyApi1, LocalDefinitionsDbService localDefinitionsDbService) {
        this.destinyApi = destinyApi1;
        this.definitionsService = localDefinitionsDbService;
    }

    @Override
    public void synchronizeInventoryFor(Account anAccount, InventoryRepository repository) {
        AccountId anAccountId = anAccount.withId();
        AccountCredentials credentials = anAccount.withCredentials();
        Inventory toUpdate = null;
        List<String> characterIds;
        if (repository.hasOfAccount(anAccountId)) {
            toUpdate = repository.ofAccount(anAccountId);
            characterIds = new ArrayList<>(toUpdate.characters().size() + 1);
            for (Character instance : toUpdate.characters())
                characterIds.add(instance.characterId());
        } else {
            BungieResponse<DataResponse<com.bungie.netplatform.destiny.representation.Account>> accountRespose = destinyApi.getAccount(anAccountId.withMembershipType(), anAccountId.withMembershipId(),
                    credentials.asCookieVal(), credentials.xcsrf());
            BungieResponseValidator.validate(accountRespose, anAccount);
            characterIds = new ArrayList<>();
            for (com.bungie.netplatform.destiny.representation.Character bungieCharacter : accountRespose.getResponse().getData().getCharacters()) {
                characterIds.add(bungieCharacter.getCharacterBase().getCharacterId());
            }
        }

        List<Character> characters = new ArrayList<>(characterIds.size());
        Vault vault;
        ItemBagTranslator translator = new ItemBagTranslator();
        List<ItemInstance> instances = new ArrayList<>();
        List<ItemDefinition> definitions;

        for (String characterId : characterIds) {
            BungieResponse<DataResponse<CharacterInventory>> inventoryResponse = destinyApi
                    .getCharacterInventory((anAccountId.withMembershipType()),
                            anAccountId.withMembershipId(),
                            characterId,
                            credentials.asCookieVal(),
                            credentials.xcsrf());

            BungieResponseValidator.validate(inventoryResponse, anAccount);
            CharacterInventory bungieInventory = inventoryResponse.getResponse().getData();

            for (Equippable equippableList : bungieInventory.getBuckets().getEquippable()) {
                instances.addAll(equippableList.getItems());
            }

            for (com.bungie.netplatform.destiny.representation.Item itemList : bungieInventory.getBuckets().getItem()) {
                instances.addAll(itemList.getItems());
            }

            definitions = definitionsService.getDefinitionsFor(instances);

            characters.add(translator.characterFrom(characterId, definitions, instances, anAccountId));
            instances.clear();
            definitions.clear();
        }

        BungieResponse<DataResponse<com.bungie.netplatform.destiny.representation.Vault>> inventoryResponse = destinyApi
                .getVault(anAccountId.withMembershipType(),
                        credentials.asCookieVal(),
                        credentials.xcsrf());
        BungieResponseValidator.validate(inventoryResponse, anAccount);
        for (com.bungie.netplatform.destiny.representation.Item bungieItems : inventoryResponse.getResponse().getData().getBuckets()) {
            instances.addAll(bungieItems.getItems());
        }

        definitions = definitionsService.getDefinitionsFor(instances);
        vault = translator.vaultFrom(definitions, instances, anAccountId);
        instances.clear();
        definitions.clear();

        Inventory toReturn = new Inventory(anAccountId, characters, vault);

//        // TODO: 11/11/16 Figure out if this can be rewritten with CompletableFutures and/or Streams to lessen Rx reliance
//        ItemBagTranslator translator = new ItemBagTranslator();
//        Inventory toReturn = Single.zip(
//                Observable.fromIterable(characterIds)
//                        .flatMap(charId -> Observable.defer(() -> Observable.just(destinyApi.getCharacterInventory((anAccountId.withMembershipType()),
//                                anAccountId.withMembershipId(),
//                                charId,
//                                credentials.asCookieVal(),
//                                credentials.xcsrf()))
//                                .map(inventoryResponse -> {
//                                    BungieResponseValidator.validate(inventoryResponse, anAccount);
//                                    CharacterInventory bungieInventory = inventoryResponse.getResponse().getData();
//
//                                    List<ItemInstance> instances = new ArrayList<>();
//                                    List<ItemDefinition> definitions;
//                                    for (Equippable equippableList : bungieInventory.getBuckets().getEquippable()) {
//                                        instances.addAll(equippableList.getItems());
//                                    }
//                                    for (com.bungie.netplatform.destiny.representation.Item itemList : bungieInventory.getBuckets().getItem()) {
//                                        instances.addAll(itemList.getItems());
//                                    }
//                                    definitions = definitionsService.getDefinitionsFor(instances);
//                                    return translator.characterFrom(charId, definitions, instances, anAccountId);
//                                }))
//                                .subscribeOn(Schedulers.io())
//                        )
//                        .toList(),
//                Single.defer(() -> Single.just(destinyApi.getVault(anAccountId.withMembershipType(),
//                        credentials.asCookieVal(),
//                        credentials.xcsrf()))
//                        .map(inventoryResponse -> {
//                            BungieResponseValidator.validate(inventoryResponse, anAccount);
//                            List<ItemInstance> instances = new ArrayList<>();
//                            List<ItemDefinition> definitions;
//                            for (com.bungie.netplatform.destiny.representation.Item bungieItems : inventoryResponse.getResponse().getData().getBuckets()) {
//                                instances.addAll(bungieItems.getItems());
//                            }
//
//                            definitions = definitionsService.getDefinitionsFor(instances);
//                            translator.vaultFrom(definitions, instances, anAccountId);
//                            return translator.vaultFrom(definitions, instances, anAccountId);
//                        }))
//                        .subscribeOn(Schedulers.io()),
//                (characters, vault) -> new Inventory(anAccountId, characters, vault))
//                .blockingGet();

        if (toUpdate != null) {
            toUpdate.updateFrom(toReturn);
        } else {
            repository.add(toReturn);
        }

        DomainEventPublisher.instanceOf().publish(new InventorySynced(toReturn));
    }

    @Override
    public void transferItem(String anItemId, String toBagId, Inventory inventory, Account anAccount) {
        AccountId anAccountId = anAccount.withId();
        AccountCredentials credentials = anAccount.withCredentials();

        ItemBag toBag = inventory.bagWithId(toBagId);
        ItemBag fromBag = inventory.bagThatContains(anItemId);

        Item item = fromBag.itemOfId(anItemId);

        if (toBagId.equals(fromBag.withId())) {
            return;
        }
        if (item.isEquipped()) {
            unequip(anItemId, (Character) fromBag, anAccount);
        }
        if (toBag instanceof Vault) {
            BungieResponse<Integer> transferResponse = destinyApi.transferItem(
                    new TransferCommand(anAccountId.withMembershipType(),
                            String.valueOf(item.getBungieItemHash()),
                            item.getBungieItemInstanceId(),
                            item.getStackSize(),
                            ((Character) fromBag).characterId(),
                            true),
                    credentials.asCookieVal(),
                    credentials.xcsrf());
            BungieResponseValidator.validate(transferResponse, anAccount);
            inventory.transferItem(anItemId, fromBag.withId(), toBagId);

        } else if (fromBag instanceof Vault) {
            BungieResponse<Integer> transferResponse = destinyApi.transferItem(
                    new TransferCommand(anAccountId.withMembershipType(),
                            String.valueOf(item.getBungieItemHash()),
                            item.getBungieItemInstanceId(),
                            item.getStackSize(),
                            ((Character) toBag).characterId(),
                            false),
                    credentials.asCookieVal(),
                    credentials.xcsrf());
            BungieResponseValidator.validate(transferResponse, anAccount);
            inventory.transferItem(anItemId, fromBag.withId(), toBagId);

        } else {
            Vault vault = inventory.vault();
            BungieResponse<Integer> transferResponse = destinyApi.transferItem(
                    new TransferCommand(anAccountId.withMembershipType(),
                            String.valueOf(item.getBungieItemHash()),
                            item.getBungieItemInstanceId(),
                            item.getStackSize(),
                            ((Character) fromBag).characterId(),
                            true),
                    credentials.asCookieVal(),
                    credentials.xcsrf());
            BungieResponseValidator.validate(transferResponse, anAccount);
            inventory.transferItem(anItemId, fromBag.withId(), vault.withId());
            transferResponse = destinyApi.transferItem(
                    new TransferCommand(anAccountId.withMembershipType(),
                            String.valueOf(item.getBungieItemHash()),
                            item.getBungieItemInstanceId(),
                            item.getStackSize(),
                            ((Character) toBag).characterId(),
                            false),
                    credentials.asCookieVal(),
                    credentials.xcsrf());
            BungieResponseValidator.validate(transferResponse, anAccount);
            inventory.transferItem(anItemId, vault.withId(), toBag.withId());
        }
    }

    @Override
    public boolean equip(String anItemId, Character onCharacter, Account anAccount) {
        Item item = onCharacter.itemOfId(anItemId);
        for (Item instance : onCharacter.items()) {
            if (instance.isEquipped() && (instance.getItemTypeHash() == item.getItemTypeHash())) {
                BungieResponse<Integer> equipResponse = destinyApi.equipItem(
                        new EquipCommand(anAccount.withId().withMembershipType(),
                                item.getBungieItemInstanceId(),
                                onCharacter.characterId()),
                        anAccount.withCredentials().asCookieVal(),
                        anAccount.withCredentials().xcsrf());
                BungieResponseValidator.validate(equipResponse, anAccount);
                onCharacter.equip(anItemId, instance.getItemId());
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean unequip(String anItemId, Character onCharacter, Account anAccount) {
        Item item = onCharacter.itemOfId(anItemId);
        for (Item instance : onCharacter.items()) {
            if (instance != item && (instance.getItemTypeHash() == item.getItemTypeHash()) && !instance.getTierTypeName().equals("Exotic")) {
                BungieResponse<Integer> equipResponse = destinyApi.equipItem(
                        new EquipCommand(anAccount.withId().withMembershipType(),
                                instance.getBungieItemInstanceId(),
                                onCharacter.characterId()),
                        anAccount.withCredentials().asCookieVal(),
                        anAccount.withCredentials().xcsrf());
                BungieResponseValidator.validate(equipResponse, anAccount);
                onCharacter.equip(instance.getItemId(), anItemId);
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<ItemType> getExoticTypes() {
        ItemBagTranslator translator = new ItemBagTranslator();
        return translator.transform(definitionsService.getExoticDefinitions());
    }
}
