/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/2/19 9:13 PM
 *
 * Last modified 7/2/19 12:21 PM
 */

package com.flipp.flopp.data.art.network;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RandomMeService {



    @GET("api/?results=100")
    Call<RandomMeResponse> getUsers();

}
