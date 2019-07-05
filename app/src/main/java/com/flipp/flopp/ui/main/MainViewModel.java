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
import com.flipp.flopp.common.architecture.Status;
import com.flipp.flopp.data.art.local.Art;
import com.flipp.flopp.data.art.repository.ArtRepository;
import com.flipp.flopp.data.user.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

/**
 * This is the view model shared by all fragments that display artwork lists in the app
 */
public class MainViewModel extends ViewModel {

    private ArtRepository artRepo ; //Art repository
    private UserRepository userRepo ; //User repository
    private LiveData<Resource<List<Art>>> allArt; //All art

    //Stores data for each page
    private LiveData<List<Art>> paintings ;
    private LiveData<List<Art>> sculptures ;
    private LiveData<List<Art>> drawings ;
    private LiveData<List<Art>> photographs ;
    private LiveData<List<Art>> designs ;

    //Used to observe the current location
    final MutableLiveData<String> city; //Location

    /**
     * Create viewmodel with Art and User Repositories as inputs
     * @param artRepository
     * @param userRepository
     */
    @Inject
    public MainViewModel(ArtRepository artRepository, UserRepository userRepository){

        city = new MutableLiveData<>(); //current city
        artRepo = artRepository;
        userRepo = userRepository;

        //If city changes update all art
        allArt = Transformations.switchMap(city, input -> {
            if (input.isEmpty()) {
                return new LiveData() {
                    @Override
                    protected void postValue(Object value) {
                        super.postValue(null);
                    }
                };
            }
                return artRepo.loadArt(city.getValue());
        });

        //For each category update the lists if all art changes.
        paintings = Transformations.switchMap(allArt, input -> {

            if(input.status == Status.SUCCESS && !input.data.isEmpty()) {
                return artRepo.getArt("Painting",getCity());
            } else return new LiveData() {
                @Override
                protected void postValue(Object value) {
                    super.postValue(null);
                }
            };
        });
        sculptures= Transformations.switchMap(allArt, input -> {
            if(input.status == Status.SUCCESS && !input.data.isEmpty()) {
                return artRepo.getArt("Sculpture",getCity());
            } else return new LiveData() {
                @Override
                protected void postValue(Object value) {
                    super.postValue(null);
                }
            };
        });

        drawings= Transformations.switchMap(allArt, input -> {
            if(input.status == Status.SUCCESS && !input.data.isEmpty()) {
                return artRepo.getArt("Drawing, Collage or other Work on Paper",getCity());
            } else return new LiveData() {
                @Override
                protected void postValue(Object value) {
                    super.postValue(null);
                }
            };
        });

        photographs= Transformations.switchMap(allArt, input -> {
            if(input.status == Status.SUCCESS && !input.data.isEmpty()) {
                return artRepo.getArt("Photography",getCity());
            } else return new LiveData() {
                @Override
                protected void postValue(Object value) {
                    super.postValue(null);
                }
            };
        });

        designs= Transformations.switchMap(allArt, input -> {
            if(input.status == Status.SUCCESS && !input.data.isEmpty()) {
                return artRepo.getArt("Design/Decorative Art",getCity());
            } else return new LiveData() {
                @Override
                protected void postValue(Object value) {
                    super.postValue(null);
                }
            };
        });
    }

    //Getters for observable data
    public LiveData<List<Art>> getSculptures() {
        return sculptures;
    }
    public LiveData<List<Art>> getPaintings() {
        return paintings;
    }
    public LiveData<List<Art>> getDrawings() {
        return drawings;
    }
    public LiveData<List<Art>> getPhotographs() {
        return photographs;
    }
    public LiveData<List<Art>> getDesigns() {
        return designs;
    }
    public LiveData<Resource<List<Art>>> getAllArt() {
        return allArt;
    }

    //Get and Set the city
    public String getCity(){
        return userRepo.getCity();
    }
    public void setCity(String city){
        userRepo.setCity(city);
        this.city.setValue(city);
    }

    //Get and Set the favorites
    public LiveData<List<Art>> getFavorites(){
        return this.artRepo.getFavorites();
    }
    public void setFavorite(String id, boolean isFavorite){
        this.artRepo.setFavorite(id,isFavorite);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
