package com.jam01.littlelight.adapter.common.service.identityaccess;

import com.bungie.netplatform.destiny.api.DestinyApi;
import com.bungie.netplatform.destiny.representation.BungieResponse;
import com.bungie.netplatform.destiny.representation.Endpoints;
import com.bungie.netplatform.destiny.representation.UserResponse;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jam01.littlelight.domain.DomainEventPublisher;
import com.jam01.littlelight.domain.identityaccess.Account;
import com.jam01.littlelight.domain.identityaccess.AccountCredentials;
import com.jam01.littlelight.domain.identityaccess.AccountCredentialsExpired;
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

        BungieResponse<JsonObject> membershipIdsResponse = destinyApi.membershipIds(credentials.asCookieVal(), credentials.xcsrf());
        if (membershipIdsResponse.getErrorCode() != 1) {
            throw new IllegalStateException(membershipIdsResponse.getMessage());
        }

        for (Map.Entry<String, JsonElement> entry : membershipIdsResponse.getResponse().entrySet()) {
            if (Integer.valueOf(entry.getValue().toString()) == membershipType) {
                membershipId = entry.getKey();
                break;
            }
        }

        BungieResponse<UserResponse> userResponse = destinyApi.getUser(credentials.asCookieVal(), credentials.xcsrf());
        if (userResponse.getErrorCode() != 1) {
            throw new IllegalStateException(userResponse.getMessage());
        }

        AccountId accountId = new AccountId(membershipType, membershipId);
        com.bungie.netplatform.destiny.representation.User user = userResponse.getResponse().getUser();
        displayName = user.getDisplayName();

        return new Account(accountId, credentials, displayName, Endpoints.BASE_URL + user.getProfilePicturePath(), membershipType == 2 ? "PlayStation" : "XBOX");
    }

    @Override
    public void synchronizeAccountsFor(User user) {
        for (Account instance : user.allRegisteredAccounts()) {
            synchronizeAccount(instance, user);
        }
    }

    @Override
    public void synchronizeAccount(Account anAccount, User user) {
        BungieResponse<UserResponse> userResponse = destinyApi.getUser(anAccount.withCredentials().asCookieVal(), anAccount.withCredentials().xcsrf());
        if (userResponse.getErrorCode() != 1) {
            if (userResponse.getErrorCode() == 99) {
                DomainEventPublisher.instanceOf().publish(new AccountCredentialsExpired(anAccount));
            }
            throw new IllegalStateException(userResponse.getMessage());
        }
        com.bungie.netplatform.destiny.representation.User bungieUser = userResponse.getResponse().getUser();
        user.updateAccount(anAccount.withId(), bungieUser.getDisplayName(), Endpoints.BASE_URL + bungieUser.getProfilePicturePath());
    }
}
