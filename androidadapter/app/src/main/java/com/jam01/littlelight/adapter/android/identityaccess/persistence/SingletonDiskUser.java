package com.jam01.littlelight.adapter.android.identityaccess.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.jam01.littlelight.domain.identityaccess.Account;
import com.littlelight.identityaccess.domain.LegendId;
import com.jam01.littlelight.domain.identityaccess.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by jam01 on 8/6/16.
 */
public class SingletonDiskUser implements User {
    private final SharedPreferences disk;
    private final Map<LegendId, Account> legendMap;
    private final Gson gson;

    public SingletonDiskUser(Context mContext) {
        disk = mContext.getSharedPreferences("user", Context.MODE_PRIVATE);
        this.gson = new Gson();
        legendMap = new HashMap<>();
        load();
    }

    @Override
    public void registerAccount(Account aAccount) {
        legendMap.put(aAccount.withId(), aAccount);
        save();
    }

    @Override
    public Account ofId(LegendId aLegendId) {
        return legendMap.get(aLegendId);
    }

    @Override
    public Collection<Account> allRegisteredAccounts() {
        return legendMap.values();
    }

    @Override
    public void unregisterLegend(Account aAccount) {
        legendMap.remove(aAccount.withId());
        save();
    }

    private void load() {
        if (disk.contains("legends")) {
            Set<String> serializedLegends = disk.getStringSet("legends", null);

            if (serializedLegends != null) {
                for (String serialized : serializedLegends) {
                    Account instance = gson.fromJson(serialized, Account.class);
                    legendMap.put(instance.withId(), instance);
                }
            }
        }
    }

    private void save() {
        if (legendMap.isEmpty()) {
            return;
        }

        Set<String> serializedLegends = new HashSet<>(legendMap.size());
        for (Account instance : legendMap.values()) {
            serializedLegends.add(gson.toJson(instance));
        }

        disk.edit().putStringSet("legends", serializedLegends).apply();
    }
}
