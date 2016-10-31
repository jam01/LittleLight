package com.jam01.littlelight.domain.identityaccess;

import com.jam01.littlelight.domain.DomainEvent;

import java.util.Date;

/**
 * Created by jam01 on 10/5/16.
 */
public class AccountRegistered implements DomainEvent {
    private final Account accountRegistered;
    private final Date occurredOn;

    public AccountRegistered(Account accountRegistered) {
        this.accountRegistered = accountRegistered;
        occurredOn = new Date();
    }

    public Account getAccountRegistered() {
        return accountRegistered;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }
}
