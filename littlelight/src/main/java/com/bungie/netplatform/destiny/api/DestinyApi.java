package com.bungie.netplatform.destiny.api;

import com.bungie.netplatform.destiny.representation.Account;
import com.bungie.netplatform.destiny.representation.CharacterInventory;
import com.bungie.netplatform.destiny.representation.ItemDefinition;
import com.bungie.netplatform.destiny.representation.ItemInstance;
import com.bungie.netplatform.destiny.representation.User;
import com.bungie.netplatform.destiny.representation.Vault;

import java.util.List;

/**
 * Created by jam01 on 8/10/16.
 */
public interface DestinyApi {
    Vault getVault(String membershipType, String cookies, String xcsrf);

    CharacterInventory getCharacterInventory(String membershipType, String membershipId, String characterId, String cookies, String xcsrf);

    boolean equipItem();

    boolean transferItem();

    Account getAccount(String membershipType, String membershipId, String cookies, String xcsrf);

    User getUser(String cookies, String xcsrf);

    String latestManifestUrl();

//    ResponseBody manifestDb(String manifestUrl);

    List<ItemDefinition> getDefinitionsFor(List<ItemInstance> instanceList);
}
