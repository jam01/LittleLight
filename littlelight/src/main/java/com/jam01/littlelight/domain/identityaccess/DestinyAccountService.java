package com.jam01.littlelight.domain.identityaccess;

/**
 * Created by jam01 on 9/22/16.
 */
public interface DestinyAccountService {
    Account accountOf(int membershipType, AccountCredentials credentials);

    void synchronizeAccountsFor(User user);

    void synchronizeAccount(Account anAccountId, User user);
}
