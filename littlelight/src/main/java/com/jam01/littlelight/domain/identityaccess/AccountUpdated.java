package com.jam01.littlelight.domain.identityaccess;

import com.jam01.littlelight.domain.DomainEvent;

import java.util.Date;

/**
 * Created by jam01 on 9/30/16.
 */
public class AccountUpdated implements DomainEvent {
    private final Account accountUpdated;

    public AccountUpdated(Account accountUpdated) {
        this.accountUpdated = accountUpdated;
    }

    public Account getAccountUpdated() {
        return accountUpdated;
    }

    @Override
    public Date occurredOn() {
        return new Date();
    }
}
