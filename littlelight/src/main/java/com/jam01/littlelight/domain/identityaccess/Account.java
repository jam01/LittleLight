package com.jam01.littlelight.domain.identityaccess;


import com.jam01.littlelight.domain.DomainEventPublisher;

/**
 * Created by jam01 on 8/5/16.
 */
public class Account {
    private final AccountId accountId;
    private final String platform;
    private AccountCredentials accountCredentials;
    private String displayName;
    private String profilePath;

    public Account(AccountId accountId, AccountCredentials accountCredentials, String displayName, String profilePath, String platform) {
        this.accountId = accountId;
        this.accountCredentials = accountCredentials;
        this.displayName = displayName;
        this.profilePath = profilePath;
        this.platform = platform;
    }

    public String onPlatform() {
        return platform;
    }

    public String profilePath() {
        return profilePath;
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

    public void updateAccount(String displayName, String profilePath) {

        if (this.displayName == null || this.profilePath == null || !this.displayName.equals(displayName) || !this.profilePath.equals(profilePath)) {
            this.displayName = displayName;
            this.profilePath = profilePath;
            DomainEventPublisher.instanceOf().publish(new AccountUpdated(this));
        }
    }

    public void updateCredentials(AccountCredentials newCredentials) {
        accountCredentials = newCredentials;
    }
}
