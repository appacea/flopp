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

@Module
public class RepoModule {

    @Singleton
    @Provides
    static AppExecutors provideExecutors() {
        return new AppExecutors();
    }


    @Singleton
    @Provides
    static ArtRepository provideArtRepo(AppExecutors appExecutors, ArtDatabase artDatabase, ArtsyService artsyService, RandomMeService randomMeService) {
        return new ArtRepository(appExecutors,artDatabase.artDao(),artsyService,randomMeService);
    }


    @Singleton
    @Provides
    static UserRepository provideUserRepo(SessionStorage sessionStorage) {
        return new UserRepository(sessionStorage);
    }

    @Singleton
    @Provides
    static SessionStorage provideSessionStorage(Application application) {
        return new SessionStorage(application);
    }
}
