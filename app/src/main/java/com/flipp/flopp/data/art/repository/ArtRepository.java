/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/1/19 9:06 PM
 *
 * Last modified 7/1/19 9:06 PM
 */

package com.flipp.flopp.data.art.repository;

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

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import retrofit2.Call;

public class ArtRepository {

    private ArtDao artDao;
    private ArtsyService artsyService;
    private RandomMeService randomMeService;

    public ArtRepository(ArtDao artDao, ArtsyService artsyService, RandomMeService randomMeService){
        this.artsyService = artsyService;
        this.artDao = artDao;
        this.randomMeService = randomMeService;
    }

    public LiveData<Resource<List<Art>>> loadArt() {
        return new NetworkBoundResource<List<Art>, ArtsyResponse, RandomMeResponse>() {


            @Override
            protected Call<ArtsyResponse> callOne() {
                return artsyService.getArt();
            }

            @Override
            protected Call<RandomMeResponse> callTwo(@NonNull ArtsyResponse item) {
                return randomMeService.getUsers();
            }

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
                }
                artDao.insertArtworks(artwork);
            }

            @NonNull
            @Override
            protected LiveData<List<Art>> loadFromDb() {
                return artDao.selectAll();
            }


        }.getAsLiveData();
    }

    public LiveData<List<Art>> getArt(String category){
        return this.artDao.getArt(category);
    }

}
