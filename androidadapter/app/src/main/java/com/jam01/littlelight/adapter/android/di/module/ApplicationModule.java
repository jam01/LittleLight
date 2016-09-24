package com.jam01.littlelight.adapter.android.di.module;

import com.jam01.littlelight.application.DestinyAccountImportService;
import com.jam01.littlelight.application.UserService;
import com.jam01.littlelight.domain.identityaccess.DestinyAccountService;
import com.jam01.littlelight.domain.identityaccess.User;

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
    DestinyAccountImportService providesAccountImportService(DestinyAccountService destinyService, User user) {
        return new DestinyAccountImportService(destinyService, user);
    }

    @Provides
    @Singleton
    UserService providesUserService(User user) {
        return new UserService(user);
    }
}
