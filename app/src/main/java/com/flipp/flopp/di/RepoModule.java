/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/4/19 5:06 PM
 *
 * Last modified 7/4/19 5:06 PM
 */

package com.flipp.flopp.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.flipp.flopp.common.architecture.AppExecutors;
import com.flipp.flopp.data.art.local.ArtDatabase;
import com.flipp.flopp.data.art.network.ArtsyService;
import com.flipp.flopp.data.art.network.RandomMeService;
import com.flipp.flopp.data.art.repository.ArtRepository;
import com.flipp.flopp.data.user.local.SessionStorage;
import com.flipp.flopp.data.user.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
/**
 * Module to generate repository injectables
 */
@Module
public class RepoModule {

    /**
     * Generate share prefrences
     * @param application
     * @return
     */
    @Singleton
    @Provides
    static SharedPreferences providePrefs(Application application) {
        String KEY = "com.flipp.flopp.user";
        return application.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    }

    /**
     * Generate executors
     * @return
     */
    @Singleton
    @Provides
    static AppExecutors provideExecutors() {
        return new AppExecutors();
    }

    /***
     * Generate Art Repository
     * @param appExecutors
     * @param artDatabase
     * @param artsyService
     * @param randomMeService
     * @return
     */
    @Singleton
    @Provides
    static ArtRepository provideArtRepo(AppExecutors appExecutors, ArtDatabase artDatabase, ArtsyService artsyService, RandomMeService randomMeService) {
        return new ArtRepository(appExecutors,artDatabase.artDao(),artsyService,randomMeService);
    }

    /**
     * Generate User Repository
     * @param sessionStorage
     * @return
     */
    @Singleton
    @Provides
    static UserRepository provideUserRepo(SessionStorage sessionStorage) {
        return new UserRepository(sessionStorage);
    }

    /**
     * Generate session storage
     * @param prefs
     * @return
     */
    @Singleton
    @Provides
    static SessionStorage provideSessionStorage(SharedPreferences prefs) {
        return new SessionStorage(prefs);
    }
}
