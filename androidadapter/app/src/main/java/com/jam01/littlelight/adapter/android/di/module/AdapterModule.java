package com.jam01.littlelight.adapter.android.di.module;

import android.content.Context;

import com.bungie.netplatform.destiny.api.DestinyApi;
import com.jam01.littlelight.adapter.android.service.common.RetrofitDestinyApiFacade;

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
    DestinyApi providesDestinyApi(Context context) {
        return new RetrofitDestinyApiFacade(context);
    }
}