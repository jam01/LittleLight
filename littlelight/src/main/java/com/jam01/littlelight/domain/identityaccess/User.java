package com.jam01.littlelight.domain.identityaccess;

import java.util.Collection;

/**
 * Created by jam01 on 8/6/16.
 */
public interface User {
    void registerAccount(Account aAccount);

    Account ofId(AccountId aAccountId);

    Collection<Account> allRegisteredAccounts();

    void unregisterAccount(AccountId anAccountId);

    AccountCredentials credentialsOf(AccountId anAccountId);
}
