package com.jam01.littlelight.adapter.common.service;

import com.bungie.netplatform.destiny.representation.BungieResponse;
import com.jam01.littlelight.domain.DomainEventPublisher;
import com.jam01.littlelight.domain.identityaccess.Account;
import com.jam01.littlelight.domain.identityaccess.AccountCredentialsExpired;

/**
 * Created by jam01 on 11/10/16.
 */
public class BungieResponseValidator {
    public static void validate(BungieResponse response, Account anAccount) {
        if (response.getErrorCode() != 1) {
            if (response.getErrorCode() == 99) {
                DomainEventPublisher.instanceOf().publish(new AccountCredentialsExpired(anAccount));
            }
            throw new BungieResponseException(response.getMessage());
        }
    }
}
