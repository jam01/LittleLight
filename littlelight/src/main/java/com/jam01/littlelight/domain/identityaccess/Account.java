package com.jam01.littlelight.domain.identityaccess;


/**
 * Created by jam01 on 8/5/16.
 */
public class Account {
    private final AccountId accountId;
    private final DestinyCredentials destinyCredentials;
    private String displayName;

    public Account(AccountId accountId, DestinyCredentials destinyCredentials) {
        this.accountId = accountId;
        this.destinyCredentials = destinyCredentials;
    }

    protected void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String withName() {
        return displayName;
    }

    public AccountId withId() {
        return accountId;
    }

    public DestinyCredentials withCredentials() {
        return destinyCredentials;
    }
}
