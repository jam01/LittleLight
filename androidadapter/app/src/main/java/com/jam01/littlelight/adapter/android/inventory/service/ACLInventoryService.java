package com.jam01.littlelight.adapter.android.inventory.service;

import com.bungie.netplatform.destiny.api.DestinyApi;
import com.bungie.netplatform.destiny.api.EquipCommand;
import com.bungie.netplatform.destiny.api.TransferCommand;
import com.bungie.netplatform.destiny.representation.CharacterInventory;
import com.bungie.netplatform.destiny.representation.Equippable;
import com.bungie.netplatform.destiny.representation.ItemDefinition;
import com.bungie.netplatform.destiny.representation.ItemInstance;
import com.jam01.littlelight.domain.identityaccess.Account;
import com.jam01.littlelight.domain.identityaccess.AccountCredentials;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.inventory.Character;
import com.jam01.littlelight.domain.inventory.DestinyInventoryService;
import com.jam01.littlelight.domain.inventory.Inventory;
import com.jam01.littlelight.domain.inventory.Item;
import com.jam01.littlelight.domain.inventory.ItemBag;
import com.jam01.littlelight.domain.inventory.Vault;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jam01 on 7/25/16.
 */
public class ACLInventoryService implements DestinyInventoryService {
    private final DestinyApi destinyApi;
    private List<String> characterIds;

    public ACLInventoryService(DestinyApi destinyApi1) {
        this.destinyApi = destinyApi1;
    }

    @Override
    public Inventory ofAccount(Account anAccount) {
        AccountId anAccountId = anAccount.withId();
        AccountCredentials credentials = anAccount.withCredentials();

        if (characterIds == null) {
            characterIds = new ArrayList<>();
            for (com.bungie.netplatform.destiny.representation.Character bungieCharacter : destinyApi.getAccount(anAccountId.withMembershipType(), anAccountId.withMembershipId(),
                    credentials.asCookieVal(), credentials.xcsrf()).getCharacters()) {
                characterIds.add(bungieCharacter.getCharacterBase().getCharacterId());
            }
        }

        List<Character> characters = new ArrayList<>(characterIds.size() + 1);
        Vault vault;
        ItemBagTranslator translator = new ItemBagTranslator();
        List<ItemInstance> instances = new ArrayList<>();
        List<ItemDefinition> definitions;

        for (String characterId : characterIds) {
            CharacterInventory bungieInventory = destinyApi
                    .getCharacterInventory((anAccountId.withMembershipType()),
                            anAccountId.withMembershipId(),
                            characterId,
                            credentials.asCookieVal(),
                            credentials.xcsrf());

            for (Equippable equippableList : bungieInventory.getBuckets().getEquippable()) {
                instances.addAll(equippableList.getItems());
            }

            for (com.bungie.netplatform.destiny.representation.Item itemList : bungieInventory.getBuckets().getItem()) {
                instances.addAll(itemList.getItems());
            }

            definitions = destinyApi.getDefinitionsFor(instances);

            characters.add(translator.characterFrom(characterId, definitions, instances, anAccountId));

            instances.clear();
            definitions.clear();
        }

        com.bungie.netplatform.destiny.representation.Vault bungieVault = destinyApi
                .getVault(anAccountId.withMembershipType(),
                        credentials.asCookieVal(),
                        credentials.xcsrf());

        for (com.bungie.netplatform.destiny.representation.Item bungieItems : bungieVault.getBuckets()) {
            instances.addAll(bungieItems.getItems());
        }

        definitions = destinyApi.getDefinitionsFor(instances);

        vault = translator.vaultFrom(definitions, instances, anAccountId);

        instances.clear();
        definitions.clear();

        return new Inventory(anAccountId, characters, vault);
    }

    @Override
    public void transferItem(String anItemId, String toBagId, Inventory inventory, Account anAccount) {
        AccountId anAccountId = anAccount.withId();
        AccountCredentials credentials = anAccount.withCredentials();

        ItemBag toBag = inventory.bagWithId(toBagId);
        ItemBag fromBag = inventory.bagThatContains(anItemId);

        Item item = fromBag.itemOfId(anItemId);

        if (toBag instanceof Vault) {
            destinyApi.transferItem(
                    new TransferCommand(anAccountId.withMembershipType(),
                            String.valueOf(item.getItemHash()),
                            anItemId,
                            item.getStackSize(),
                            ((Character) fromBag).characterId(),
                            true),
                    credentials.asCookieVal(),
                    credentials.xcsrf());
            inventory.transferItem(anItemId, fromBag.withId(), toBagId);
        } else if (fromBag instanceof Vault) {
            destinyApi.transferItem(
                    new TransferCommand(anAccountId.withMembershipType(),
                            String.valueOf(item.getItemHash()),
                            anItemId,
                            item.getStackSize(),
                            ((Character) toBag).characterId(),
                            false),
                    credentials.asCookieVal(),
                    credentials.xcsrf());
            inventory.transferItem(anItemId, fromBag.withId(), toBagId);
        } else {
            Vault vault = inventory.vault();

            destinyApi.transferItem(
                    new TransferCommand(anAccountId.withMembershipType(),
                            String.valueOf(item.getItemHash()),
                            anItemId,
                            item.getStackSize(),
                            ((Character) fromBag).characterId(),
                            true),
                    credentials.asCookieVal(),
                    credentials.xcsrf());
            inventory.transferItem(anItemId, fromBag.withId(), vault.withId());


            destinyApi.transferItem(
                    new TransferCommand(anAccountId.withMembershipType(),
                            String.valueOf(item.getItemHash()),
                            anItemId,
                            item.getStackSize(),
                            ((Character) toBag).characterId(),
                            false),
                    credentials.asCookieVal(),
                    credentials.xcsrf());
            inventory.transferItem(anItemId, vault.withId(), toBag.withId());
        }
    }

    @Override
    public boolean equip(String anItemId, Character onCharacter, Account anAccount) {
        return destinyApi.equipItem(
                new EquipCommand(anAccount.withId().withMembershipType(),
                        anItemId,
                        onCharacter.characterId()),
                anAccount.withCredentials().asCookieVal(),
                anAccount.withCredentials().xcsrf());
    }
}
