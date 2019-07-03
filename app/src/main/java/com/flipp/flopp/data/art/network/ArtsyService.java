/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/1/19 6:34 PM
 *
 * Last modified 7/1/19 6:34 PM
 */

package com.flipp.flopp.data.art.network;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ArtsyService {


    @POST("api/tokens/xapp_token")
    Call<ArtsyToken> getToken(@Body ArtsyToken token);

    @GET("api/artworks")
    Call<ArtsyResponse> getArt();

}
