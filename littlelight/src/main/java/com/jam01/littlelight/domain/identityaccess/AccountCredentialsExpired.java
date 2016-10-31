package com.jam01.littlelight.domain.identityaccess;

import com.jam01.littlelight.domain.DomainEvent;

import java.util.Date;

/**
 * Created by jam01 on 10/5/16.
 */
public class AccountCredentialsExpired implements DomainEvent {
    private final Account expiredAccount;
    private final Date occuredOn;


    public AccountCredentialsExpired(Account expiredAccount) {
        this.expiredAccount = expiredAccount;
        this.occuredOn = new Date();
    }

    public Account getExpiredAccount() {
        return expiredAccount;
    }

    @Override
    public Date occurredOn() {
        return this.occuredOn;
    }
}
