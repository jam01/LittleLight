package com.jam01.littlelight.adapter.android.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jam01 on 9/24/16.
 */

@Module
public class AndroidModule {
    Application mApplication;

    public AndroidModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return mApplication;
    }
}
