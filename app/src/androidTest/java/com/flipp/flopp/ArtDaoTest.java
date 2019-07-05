/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/4/19 1:20 PM
 *
 * Last modified 7/4/19 1:15 PM
 */

package com.flipp.flopp;

import android.content.Context;

import com.flipp.flopp.data.art.local.Art;
import com.flipp.flopp.data.art.local.ArtDao;
import com.flipp.flopp.data.art.local.ArtDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ArtDaoTest {


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    private ArtDao artDao;
    private ArtDatabase db;

    /**
     * Create the Database to test with
     */
    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, ArtDatabase.class)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build();
        artDao = db.artDao();
    }

    /**
     * Close the database when done.
     */
    @After
    public void closeDb() {
        db.close();
    }

    /**
     * Test and insert and read
     *
     * @throws Exception
     */
    @Test
    public void insertAndGetArt() throws Exception {
        Art art = new Art();
        art.setId("1");
        artDao.insertArt(art);
        List<Art> allArt = LiveDataUtil.getValue(artDao.selectAll());
        assertEquals(allArt.get(0).getId(), art.getId());
    }

    /***
     * Test get all Artwork from database
     *
     * @throws Exception
     */
    @Test
    public void getAllWork() throws Exception {
        Art art1 = new Art();
        art1.setId("1");
        artDao.insertArt(art1);
        Art art2 = new Art();
        art2.setId("2");
        artDao.insertArt(art2);
        List<Art> allArt = LiveDataUtil.getValue(artDao.selectAll());
        assertEquals(allArt.get(0).getId(), art1.getId());
        assertEquals(allArt.get(1).getId(), art2.getId());
    }

    /***
     * Test delete all artwork from database
     * @throws Exception
     */
    @Test
    public void deleteAll() throws Exception {
        Art art1 = new Art();
        art1.setId("1");
        artDao.insertArt(art1);
        Art art2 = new Art();
        art2.setId("2");
        artDao.insertArt(art2);
        artDao.deleteAll();
        List<Art> allArt = LiveDataUtil.getValue(artDao.selectAll());
        assertTrue(allArt.isEmpty());
    }


    /***
     * Test select all by city
     *
     */
    @Test
    public void selectAllByCity(){
        //TODO
    }


    /***
     * Test select all by city is null
     *
     */
    @Test
    public void selectAllByCityNull(){
        //TODO
    }


    /***
     * Test select all by category and city
     *
     */
    @Test
    public void selectAllByCategoryCity(){
        //TODO
    }

    /***
     * Test select all by category and city both Null
     *
     */
    @Test
    public void selectAllByCategoryCityNull(){
        //TODO
    }
}


