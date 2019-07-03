/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/1/19 2:03 PM
 *
 * Last modified 7/1/19 2:03 PM
 */

package com.flipp.flopp.ui.main;

import com.flipp.flopp.common.architecture.Resource;
import com.flipp.flopp.data.art.local.Art;
import com.flipp.flopp.data.art.local.ArtDatabase;
import com.flipp.flopp.data.art.network.ArtsyService;
import com.flipp.flopp.data.art.network.RandomMeService;
import com.flipp.flopp.data.art.repository.ArtRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private ArtRepository repo ;
    private LiveData<Resource<List<Art>>> allArt;
    MutableLiveData<String> modelFilter = new MutableLiveData<>();
    private LiveData<List<Art>> filteredArt  = Transformations.switchMap(modelFilter,
            new Function<String, LiveData<List<Art>>>() {
        @Override
        public LiveData<List<Art>> apply(String category) {
            return repo.getArt(category);
        }
    });

    @Inject
    public MainViewModel(ArtDatabase artDatabase, ArtsyService artsyService, RandomMeService randomMeService){
        repo = new ArtRepository(artDatabase.artDao(),artsyService,randomMeService);
        allArt = repo.loadArt();
    }


    public LiveData<Resource<List<Art>>> getAllArt() {
        return allArt;
    }


    public LiveData<List<Art>> getFilteredArt() {
        return filteredArt;
    }

    public void filterModel(String category) {
        modelFilter.postValue(category);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}