package com.jam01.littlelight.adapter.android.di.module;

import android.content.Context;

import com.bungie.netplatform.destiny.api.DestinyApi;
import com.jam01.littlelight.adapter.android.persistence.identityaccess.DiskUser;
import com.jam01.littlelight.adapter.common.persistence.inventory.InMemoryInventoryRepository;
import com.jam01.littlelight.adapter.common.service.identityaccess.ACLAccountService;
import com.jam01.littlelight.adapter.common.service.inventory.service.ACLInventoryService;
import com.jam01.littlelight.domain.identityaccess.DestinyAccountService;
import com.jam01.littlelight.domain.identityaccess.User;
import com.jam01.littlelight.domain.inventory.DestinyInventoryService;
import com.jam01.littlelight.domain.inventory.InventoryRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jam01 on 9/24/16.
 */

@Module
public class DomainModule {
    @Provides
    @Singleton
    DestinyAccountService providesDestinyAccountService(DestinyApi destinyApi) {
        return new ACLAccountService(destinyApi);
    }

    @Provides
    @Singleton
    DestinyInventoryService providesDestinyInventoryService(DestinyApi destinyApi) {
        return new ACLInventoryService(destinyApi);
    }

    @Provides
    @Singleton
    User providesUser(Context context) {
        return new DiskUser(context);
    }

    @Provides
    @Singleton
    InventoryRepository providesInventoryRepository() {
        return new InMemoryInventoryRepository();
    }
}
