package com.jam01.littlelight.adapter.android.di.module;

import com.jam01.littlelight.application.InventoryService;
import com.jam01.littlelight.application.UserService;
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
public class ApplicationModule {
    @Provides
    @Singleton
    UserService providesUserService(DestinyAccountService destinyService, User user) {
        return new UserService(destinyService, user);
    }

    @Provides
    @Singleton
    InventoryService providesInventoryService(DestinyInventoryService destinyService, InventoryRepository inventoryRepo, User user) {
        return new InventoryService(destinyService, inventoryRepo, user);
    }
}
