package com.jam01.littlelight.application;

import com.jam01.littlelight.domain.DomainEvent;
import com.jam01.littlelight.domain.DomainEventPublisher;
import com.jam01.littlelight.domain.identityaccess.Account;
import com.jam01.littlelight.domain.identityaccess.AccountCredentials;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.identityaccess.AccountUpdated;
import com.jam01.littlelight.domain.identityaccess.DestinyAccountService;
import com.jam01.littlelight.domain.identityaccess.User;

import java.util.Collection;

import rx.Observable;
import rx.functions.Func1;

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

    public void register(Account anAccount) {
        user.registerAccount(anAccount);
    }

    public void registerFromCredentials(int membershipType, AccountCredentials credentials) {
        register(fromCredentials(membershipType, credentials));
    }

    public Account fromCredentials(int membershipType, AccountCredentials credentials) {
        return destinyService.accountOf(membershipType, credentials);
    }

    public User getUser() {
        return user;
    }

    public void synchronizeUserAccounts() {
        destinyService.synchronizeAccountsFor(user);
    }


    public Observable<DomainEvent> subscribeToUserEvents() {
        return DomainEventPublisher.instanceOf().getEvents()
                .filter(new Func1<DomainEvent, Boolean>() {
                    @Override
                    public Boolean call(DomainEvent domainEvent) {
                        return domainEvent instanceof AccountUpdated;
                    }
                });
    }
}
