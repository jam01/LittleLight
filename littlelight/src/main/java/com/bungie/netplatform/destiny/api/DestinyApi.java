package com.bungie.netplatform.destiny.api;

import com.bungie.netplatform.destiny.representation.Account;
import com.bungie.netplatform.destiny.representation.BungieResponse;
import com.bungie.netplatform.destiny.representation.CharacterInventory;
import com.bungie.netplatform.destiny.representation.DataResponse;
import com.bungie.netplatform.destiny.representation.ItemDefinition;
import com.bungie.netplatform.destiny.representation.ItemInstance;
import com.bungie.netplatform.destiny.representation.UserResponse;
import com.bungie.netplatform.destiny.representation.Vault;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * Created by jam01 on 8/10/16.
 */
public interface DestinyApi {
    BungieResponse<DataResponse<Vault>> getVault(int membershipType, String cookies, String xcsrf);

    BungieResponse<DataResponse<CharacterInventory>> getCharacterInventory(int membershipType, String membershipId, String characterId, String cookies, String xcsrf);

    BungieResponse<Integer> equipItem(EquipCommand command, String cookies, String xcsrf);

    BungieResponse<Integer> transferItem(TransferCommand command, String cookies, String xcsrf);

    BungieResponse<DataResponse<Account>> getAccount(int membershipType, String membershipId, String cookies, String xcsrf);

    BungieResponse<UserResponse> getUser(String cookies, String xcsrf);

    BungieResponse<JsonObject> membershipIds(String cookies, String xcsrf);

    BungieResponse<JsonObject> latestManifestUrl();

//    ResponseBody manifestDb(String manifestUrl);

    List<ItemDefinition> getDefinitionsFor(List<ItemInstance> instanceList);
}
