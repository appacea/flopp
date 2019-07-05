/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/1/19 1:47 PM
 *
 * Last modified 7/1/19 1:47 PM
 */

package com.flipp.flopp.data.art.local;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ArtDao {

    @Insert
    Long insertArt(Art art);


    @Query("SELECT * FROM `Art` WHERE id =:artId")
    LiveData<Art> getArt(int artId);


    @Query("SELECT * FROM `Art` WHERE category =:category")
    LiveData<List<Art>> getArt(String category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArtworks(List<Art> art);

    @Query("SELECT * FROM `Art`")
    LiveData<List<Art>> selectAll();


    @Query("DELETE FROM `Art`")
    void deleteAll();


    @Query("SELECT * FROM `Art` WHERE category =:category AND city =:city")
    LiveData<List<Art>> getArt(String category, String city);


    @Query("SELECT * FROM `Art` WHERE city =:city")
    LiveData<List<Art>> selectAllByCity(String city);

    @Query("UPDATE `Art` SET isFavorite=:isFavorite WHERE id = :id")
    void setFavorite(String id, boolean isFavorite);


    @Query("SELECT * FROM `Art` WHERE isFavorite=1")
    LiveData<List<Art>> getFavorites();
}
