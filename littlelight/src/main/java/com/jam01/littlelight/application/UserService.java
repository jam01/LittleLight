package com.jam01.littlelight.application;

import com.jam01.littlelight.domain.DomainEvent;
import com.jam01.littlelight.domain.DomainEventPublisher;
import com.jam01.littlelight.domain.identityaccess.Account;
import com.jam01.littlelight.domain.identityaccess.AccountCredentials;
import com.jam01.littlelight.domain.identityaccess.AccountCredentialsExpired;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.identityaccess.AccountUpdated;
import com.jam01.littlelight.domain.identityaccess.DestinyAccountService;
import com.jam01.littlelight.domain.identityaccess.User;

import java.util.Collection;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

/**
 * Created by jam01 on 8/6/16.
 */
public class UserService {
    private DestinyAccountService destinyService;
    private User user;

    public UserService(DestinyAccountService destinyService, User user) {
        this.destinyService = destinyService;
        this.user = user;
    }

    public Collection<Account> userAccounts() {
        return user.allRegisteredAccounts();
    }

    public void unregister(AccountId anAccountId) {
        user.unregisterAccount(anAccountId);
    }

    public Account registerFromCredentials(int membershipType, String[] cookies) {
        Account toReturn = destinyService.accountOf(membershipType, AccountCredentials.instanceFrom(cookies));
        user.registerAccount(toReturn);
        return toReturn;
    }

    public void updateCredentials(AccountId accountId, String[] cookies) {
        user.updateAccountCredentials(accountId, AccountCredentials.instanceFrom(cookies));
    }

    public User getUser() {
        return user;
    }

    public void synchronizeUserAccounts() {
        destinyService.synchronizeAccountsFor(user);
    }

    public void synchronizeAccount(AccountId anAccountId) {
        destinyService.synchronizeAccount(user.ofId(anAccountId), user);
    }

    public Observable<DomainEvent> subscribeToUserEvents() {
        return DomainEventPublisher.instanceOf().getEvents()
                .filter(new Predicate<DomainEvent>() {
                    @Override
                    public boolean test(DomainEvent domainEvent) throws Exception {
                        return (domainEvent instanceof AccountUpdated || domainEvent instanceof AccountCredentialsExpired);
                    }
                });
    }
}
