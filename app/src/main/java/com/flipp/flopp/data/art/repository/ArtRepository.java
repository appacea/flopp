/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/1/19 9:06 PM
 *
 * Last modified 7/1/19 9:06 PM
 */

package com.flipp.flopp.data.art.repository;


import com.flipp.flopp.common.architecture.ApiResponse;
import com.flipp.flopp.common.architecture.AppExecutors;
import com.flipp.flopp.common.architecture.NetworkBoundResource;
import com.flipp.flopp.common.architecture.Resource;
import com.flipp.flopp.data.art.local.Art;
import com.flipp.flopp.data.art.local.ArtDao;
import com.flipp.flopp.data.art.local.ArtOwner;
import com.flipp.flopp.data.art.network.ArtsyResponse;
import com.flipp.flopp.data.art.network.ArtsyService;
import com.flipp.flopp.data.art.network.RandomMeResponse;
import com.flipp.flopp.data.art.network.RandomMeService;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

/***
 * Repository for ARt data
 */
public class ArtRepository {

    private ArtDao artDao;
    private ArtsyService artsyService;
    private RandomMeService randomMeService;
    private AppExecutors appExecutors;

    /***
     * Create instance of Art repository
     * @param appExecutors - executors to run networkboundresource
     * @param artDao - the art DAO
     * @param artsyService - artsy api used to generate dummy art data
     * @param randomMeService - random me api used to generate dummy art owners
     */
    @Inject
    public ArtRepository(AppExecutors appExecutors, ArtDao artDao, ArtsyService artsyService, RandomMeService randomMeService){
        this.artsyService = artsyService;
        this.artDao = artDao;
        this.randomMeService = randomMeService;
        this.appExecutors = appExecutors;
    }

    /****
     * Fetch artwork from API and store it locally
     *
     * Here we just fetch each time art is requested, in the future would want to have a better mechanism for determining when to fetch.
     * @param city
     * @return
     */
    public LiveData<Resource<List<Art>>> loadArt(final String city) {
        return new NetworkBoundResource<List<Art>, ArtsyResponse, RandomMeResponse>(appExecutors) {


            @Override
            protected void saveCallResult(@NonNull ArtsyResponse responseArtwork, @NonNull RandomMeResponse responseUsers) {
                List<Art> artwork = responseArtwork.get_embedded().getArtworks();
                Random rand = new Random();
                for(Art art: artwork){
                    RandomMeResponse.User user = responseUsers.getResults().get(rand.nextInt(responseUsers.getResults().size()));
                    ArtOwner owner = new ArtOwner();
                    owner.setName(user.name.getName());
                    owner.setThumbnail(user.picture.thumbnail);
                    art.setOwner(owner);
                    art.setCity(city); //Hack to generate art by city
                }
                artDao.insertArtworks(artwork);
            }


            @Override
            protected boolean shouldFetch(@Nullable List<Art> data) {
                //Always fetch since this is a demo
                //TODO: check if online first.
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Art>> loadFromDb() {
                return artDao.selectAllByCity(city);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ArtsyResponse>> createCall() {
                //Map from city to partner id to simulate city change
                String partner_id = "";
                switch(city){
                    case "Montreal":
                        partner_id = "56f94caf139b21737200320b";
                        break;
                    case "Toronto":
                        partner_id = "5554bf037261697700010000";
                        break;
                    case "Ottawa":
                        partner_id = "547cbaf47261692d5e2f0200";
                        break;
                    default:
                        partner_id = "56f94caf139b21737200320b";

                }
                return artsyService.getArt(partner_id);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RandomMeResponse>> createCall2() {
                return randomMeService.getUsers();
            }


        }.asLiveData();
    }

    /**
     * Get art by category
     * @param category
     * @return
     */
    public LiveData<List<Art>> getArt(String category){
        return this.artDao.getArt(category);
    }

    /***
     * Get art by category and city
     * @param category
     * @param city
     * @return
     */
    public LiveData<List<Art>> getArt(String category, String city){
        return this.artDao.getArt(category,city);
    }

    /**
     * Get favorites
     * @return
     */
    public LiveData<List<Art>> getFavorites(){
        return this.artDao.getFavorites();
    }

    /**
     * Set art as Favorite
     * @param id
     * @param isFavorite
     */
    public void setFavorite(String id, boolean isFavorite){
        appExecutors.diskIO().execute(() -> {
            this.artDao.setFavorite(id, isFavorite);
        });
    }

}
