/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/1/19 6:47 PM
 *
 * Last modified 7/1/19 6:47 PM
 */

package com.flipp.flopp.di;


import android.app.Application;

import com.flipp.flopp.common.architecture.LiveDataCallAdapterFactory;
import com.flipp.flopp.data.art.network.ArtsyService;
import com.flipp.flopp.data.art.network.ArtsyServiceHolder;
import com.flipp.flopp.data.art.network.RandomMeService;
import com.flipp.flopp.data.art.network.RequestInterceptor;
import com.flipp.flopp.data.art.network.TokenAuthenticator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Module to generate api injectables
 */
@Module
public class NetworkModule {

    private static final String ARTSY_BASE_URL = "https://api.artsy.net/";
    private static final String RANDOMME_BASE_URL = "https://randomuser.me/";

    /**
     * Generate GSON
     * @return
     */
    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }


    /**
     * Generate Cache config
     * @param application
     * @return
     */
    @Provides
    @Singleton
    Cache provideCache(Application application) {
        long cacheSize = 10 * 1024 * 1024; // 10 MB
        File httpCacheDirectory = new File(application.getCacheDir(), "http-cache");
        return new Cache(httpCacheDirectory, cacheSize);
    }

    /**
     * Generate Artsy API service holder
     * Use holder pattern to avoid circular dependency in dagger since http client requires service and service requires http client
     * @return
     */
    @Provides
    @Singleton
    ArtsyServiceHolder artsyServiceHolder() {
        return new ArtsyServiceHolder();
    }

    /**
     * Generate http client
     * @param application
     * @param cache
     * @param artsyServiceHolder
     * @return
     */
    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(Application application, Cache cache, ArtsyServiceHolder artsyServiceHolder) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.cache(cache);
        httpClient.addInterceptor(logging);
        httpClient.addNetworkInterceptor(new RequestInterceptor(application));
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.authenticator(new TokenAuthenticator(application,artsyServiceHolder));
        return httpClient.build();
    }

    /**
     * Generate retrofit config for artsy api service
     * @param gson
     * @param okHttpClient
     * @return
     */
    @Singleton
    @Provides
    @Named("artsy")
    static Retrofit provideArtsyRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder().baseUrl(ARTSY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(okHttpClient)
                .build();
    }


    /***
     * Generate retrofit config for randomme api service
     * @param gson
     * @param okHttpClient
     * @return
     */
    @Singleton
    @Provides
    @Named("randomme")
    static Retrofit provideRandomeMeRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder().baseUrl(RANDOMME_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(okHttpClient)
                .build();
    }

    /**
     * Generate artsy api service
     * @param retrofit
     * @param artsyServiceHolder
     * @return
     */
    @Singleton
    @Provides
    static ArtsyService provideRetrofitArtsyService(@Named("artsy") Retrofit retrofit, ArtsyServiceHolder artsyServiceHolder) {
        ArtsyService artsyService = retrofit.create(ArtsyService.class);
        artsyServiceHolder.setArtsyService(artsyService);
        return artsyService;
    }

    /**
     * Generate randomme api service
     * @param retrofit
     * @return
     */
    @Singleton
    @Provides
    static RandomMeService provideRetrofitUserService(@Named("randomme") Retrofit retrofit) {
        return retrofit.create(RandomMeService.class);
    }
}
