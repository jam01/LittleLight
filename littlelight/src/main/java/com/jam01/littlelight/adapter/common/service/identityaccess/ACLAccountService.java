package com.jam01.littlelight.adapter.common.service.identityaccess;

import com.bungie.netplatform.destiny.api.DestinyApi;
import com.bungie.netplatform.destiny.representation.Endpoints;
import com.google.gson.JsonElement;
import com.jam01.littlelight.domain.identityaccess.Account;
import com.jam01.littlelight.domain.identityaccess.AccountCredentials;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.identityaccess.DestinyAccountService;
import com.jam01.littlelight.domain.identityaccess.User;

import java.util.Map;

/**
 * Created by jam01 on 9/22/16.
 */
public class ACLAccountService implements DestinyAccountService {
    private final DestinyApi destinyApi;

    public ACLAccountService(DestinyApi destinyApi) {
        this.destinyApi = destinyApi;
    }

    @Override
    public Account accountOf(int membershipType, AccountCredentials credentials) {
        String membershipId = null;
        String displayName;

        for (Map.Entry<String, JsonElement> entry : destinyApi.membershipIds(credentials.asCookieVal(), credentials.xcsrf()).entrySet()) {
            if (Integer.valueOf(entry.getValue().toString()) == membershipType) {
                membershipId = entry.getKey();
                break;
            }
        }
        AccountId accountId = new AccountId(membershipType, membershipId);
        com.bungie.netplatform.destiny.representation.User user = destinyApi.getUser(credentials.asCookieVal(), credentials.xcsrf());
        displayName = user.getDisplayName();

        return new Account(accountId, credentials, displayName, Endpoints.BASE_URL + user.getProfilePicturePath());
    }

    @Override
    public void synchronizeAccountsFor(User user) {
        for (Account instance : user.allRegisteredAccounts()) {
            com.bungie.netplatform.destiny.representation.User bungieUser = destinyApi.getUser(instance.withCredentials().asCookieVal(), instance.withCredentials().xcsrf());
            user.updateAccount(instance.withId(), bungieUser.getDisplayName(), Endpoints.BASE_URL + bungieUser.getProfilePicturePath());
        }
    }
}
