package com.jam01.littlelight.adapter.android.persistence.identityaccess;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.jam01.littlelight.domain.DomainEventPublisher;
import com.jam01.littlelight.domain.identityaccess.Account;
import com.jam01.littlelight.domain.identityaccess.AccountCredentials;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.identityaccess.AccountRegistered;
import com.jam01.littlelight.domain.identityaccess.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by jam01 on 8/6/16.
 */
public class DiskUser implements User {
    private final SharedPreferences disk;
    private final Map<AccountId, Account> accountMap;
    private final Gson gson;
    private final String TAG = this.getClass().getSimpleName();

    public DiskUser(Context mContext, Gson gson) {
        disk = mContext.getSharedPreferences("user", Context.MODE_PRIVATE);
        this.gson = gson;
        accountMap = new HashMap<>();
        load();
    }

    @Override
    public void registerAccount(Account aAccount) {
        accountMap.put(aAccount.withId(), aAccount);
        save();
        Log.d(TAG, "registerAccount: " + aAccount.withName());
        DomainEventPublisher.instanceOf().publish(new AccountRegistered(aAccount));
    }

    @Override
    public Account ofId(AccountId aAccountId) {
        return accountMap.get(aAccountId);
    }

    @Override
    public Collection<Account> allRegisteredAccounts() {
        return accountMap.values();
    }

    @Override
    public void unregisterAccount(AccountId anAccountId) {
        accountMap.remove(anAccountId);
        save();
    }

    @Override
    public AccountCredentials credentialsOf(AccountId anAccountId) {
        return ofId(anAccountId).withCredentials();
    }

    @Override
    public void updateAccount(AccountId accountId, String displayName, String profilePath) {
        accountMap.get(accountId).updateAccount(displayName, profilePath);
        save();
    }

    @Override
    public void updateAccountCredentials(AccountId accountId, AccountCredentials newCredentials) {
        accountMap.get(accountId).updateCredentials(newCredentials);
        save();
    }

    private void load() {
        if (disk.contains("accounts")) {
            Set<String> serializedLegends = disk.getStringSet("accounts", null);

            if (serializedLegends != null) {
                for (String serialized : serializedLegends) {
                    Account instance = gson.fromJson(serialized, Account.class);
                    accountMap.put(instance.withId(), instance);
                }
            }
        }
    }

    private void save() {
        if (accountMap.isEmpty()) {
            disk.edit().remove("accounts").apply();
            return;
        }

        Set<String> serializedLegends = new HashSet<>(accountMap.size());
        for (Account instance : accountMap.values()) {
            serializedLegends.add(gson.toJson(instance));
        }

        disk.edit().putStringSet("accounts", serializedLegends).apply();
    }
}
