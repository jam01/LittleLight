package com.jam01.littlelight.domain.identityaccess;


/**
 * Created by jam01 on 8/5/16.
 */
public class Account {
    private final AccountId accountId;
    private final AccountCredentials accountCredentials;
    private String displayName;

    public Account(AccountId accountId, AccountCredentials accountCredentials) {
        this.accountId = accountId;
        this.accountCredentials = accountCredentials;
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

    public AccountCredentials withCredentials() {
        return accountCredentials;
    }
}
