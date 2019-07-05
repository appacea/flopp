/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/4/19 8:07 PM
 *
 * Last modified 7/4/19 8:07 PM
 */

package com.flipp.flopp.data.user.repository;

import com.flipp.flopp.data.user.local.SessionStorage;

import javax.inject.Inject;

public class UserRepository {

    private SessionStorage sessionStorage;

    @Inject
    public UserRepository(SessionStorage sessionStorage){
        this.sessionStorage = sessionStorage;
    }

    public String getCity(){
        return this.sessionStorage.getCity();
    }

    public void setCity(String city){
        this.sessionStorage.saveCity(city);
    }
}
