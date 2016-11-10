package com.bungie.netplatform.destiny.api;

import com.bungie.netplatform.destiny.representation.Account;
import com.bungie.netplatform.destiny.representation.Advisors;
import com.bungie.netplatform.destiny.representation.BungieResponse;
import com.bungie.netplatform.destiny.representation.CharacterInventory;
import com.bungie.netplatform.destiny.representation.DataResponse;
import com.bungie.netplatform.destiny.representation.UserResponse;
import com.bungie.netplatform.destiny.representation.Vault;
import com.google.gson.JsonObject;

import java.io.InputStream;

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

    BungieResponse<DataResponse<Advisors>> getAdvisorsForCharacter(int membershipType, String membershipId, String characterId, String cookies, String xcsrf);

    InputStream zippedManifest(String manifestUrl);
}
