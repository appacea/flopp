/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/1/19 8:35 PM
 *
 * Last modified 7/1/19 8:35 PM
 */

package com.flipp.flopp.di;

import android.app.Application;

import com.flipp.flopp.data.art.local.ArtDao;
import com.flipp.flopp.data.art.local.ArtDatabase;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

/**
 * Module to generate database injectables
 */
@Module
public class DatabaseModule {


    @Provides
    @Singleton
    ArtDatabase getArtDatabase(Application application){
        return Room.databaseBuilder(application,
                ArtDatabase.class, "db_art").fallbackToDestructiveMigration().build(); //TODO: remove fallbackToDestructiveMigration
    }

    @Provides
    @Singleton
    ArtDao getDao(ArtDatabase artDatabase){
        return artDatabase.artDao();
    }
}
