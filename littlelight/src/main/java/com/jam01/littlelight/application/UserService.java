package com.jam01.littlelight.application;

import com.jam01.littlelight.domain.identityaccess.Account;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.identityaccess.User;

import java.util.Collection;

/**
 * Created by jam01 on 8/6/16.
 */
public class UserService {
    private User user;

    public Collection<Account> userAccounts() {
        return user.allRegisteredAccounts();
    }

    public void register(Account anAccount) {
        user.registerAccount(anAccount);
    }

    public void unregister(AccountId anAccountId){
        user.unregisterAccount(anAccountId);
    }
}
