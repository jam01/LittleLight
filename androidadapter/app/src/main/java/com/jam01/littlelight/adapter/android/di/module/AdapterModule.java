package com.jam01.littlelight.adapter.android.di.module;

import android.content.Context;

import com.bungie.netplatform.destiny.api.DestinyApi;
import com.jam01.littlelight.adapter.android.service.common.RetrofitDestinyApiFacade;
import com.jam01.littlelight.adapter.android.service.inventory.AndroidLocalDefitionsDbService;
import com.jam01.littlelight.adapter.common.service.inventory.LocalDefinitionsDbService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jam01 on 9/24/16.
 */

@Module
public class AdapterModule {
    @Provides
    @Singleton
    DestinyApi providesDestinyApi() {
        return new RetrofitDestinyApiFacade();
    }

    @Provides
    @Singleton
    LocalDefinitionsDbService providesLocalDefinitionsDbService(Context context, DestinyApi destinyApi) {
        return new AndroidLocalDefitionsDbService(context, destinyApi);
    }
}