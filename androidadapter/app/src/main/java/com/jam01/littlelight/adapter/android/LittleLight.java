package com.jam01.littlelight.adapter.android;

import android.app.Application;

import com.jam01.littlelight.adapter.android.di.module.AdapterModule;
import com.jam01.littlelight.adapter.android.di.module.AndroidModule;
import com.jam01.littlelight.adapter.android.di.module.ApplicationModule;
import com.jam01.littlelight.adapter.android.di.module.DomainModule;
import com.jam01.littlelight.adapter.android.presentation.inventory.InventoryPresenter;
import com.jam01.littlelight.adapter.android.presentation.user.UserPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jam01 on 9/24/16.
 */
public class LittleLight extends Application {
    private LittleLightComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerLittleLight_LittleLightComponent.builder()
                .androidModule(new AndroidModule(this))
                .build();
    }

    public LittleLightComponent getComponent() {
        return component;
    }

    @Singleton
    @Component(modules = {AndroidModule.class, AdapterModule.class, ApplicationModule.class, DomainModule.class})
    public interface LittleLightComponent {
        UserPresenter provideMainPresenter();

        InventoryPresenter provideInventoryPresenter();
    }
}
