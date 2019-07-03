/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/1/19 1:47 PM
 *
 * Last modified 7/1/19 1:47 PM
 */

package com.flipp.flopp.data.art.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Art.class}, version = 1, exportSchema = false)
@TypeConverters({StringListConverter.class,ArtLinksConverter.class})
public abstract class ArtDatabase extends RoomDatabase {

    public abstract ArtDao artDao();
}