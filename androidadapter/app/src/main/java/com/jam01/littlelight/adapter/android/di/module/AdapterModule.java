package com.jam01.littlelight.adapter.android.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bungie.netplatform.destiny.api.DestinyApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.jam01.littlelight.adapter.android.service.common.RetrofitDestinyApiFacade;
import com.jam01.littlelight.adapter.android.service.inventory.AndroidLocalDefitionsDbService;
import com.jam01.littlelight.adapter.common.service.inventory.LocalDefinitionsDbService;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jam01 on 9/24/16.
 */

@Module
public class AdapterModule {
    String mBaseUrl;

    public AdapterModule(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

    @Provides
    @Singleton
    DestinyApi providesDestinyApi(Retrofit retrofit) {
        return new RetrofitDestinyApiFacade(retrofit);
    }

    @Provides
    @Singleton
    LocalDefinitionsDbService providesLocalDefinitionsDbService(Context context, DestinyApi destinyApi) {
        return new AndroidLocalDefitionsDbService(context, destinyApi);
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Context application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    Cache providesOkHttpCache(Context application) {
        int cacheSize = 50 * 1024 * 1024; // 50 MiB
        return new Cache(application.getCacheDir(), cacheSize);
    }

    @Provides
    @Singleton
    Gson providesGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient(Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }


    @Provides
    @Singleton
    Picasso providesPicasso(Context context, OkHttpClient okHttpClient) {
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(okHttpClient))
//                .indicatorsEnabled(true)
                .build();
    }


}