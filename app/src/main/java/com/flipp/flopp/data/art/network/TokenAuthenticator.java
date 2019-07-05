/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/1/19 11:12 PM
 *
 * Last modified 7/1/19 11:12 PM
 */

package com.flipp.flopp.data.art.network;


import android.content.Context;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/***
 * Http Authenticator used to request Artsy access token
 */
public class TokenAuthenticator implements Authenticator {

    private Context context;
    private ArtsyServiceHolder artsyServiceHolder;

    public TokenAuthenticator(Context context, ArtsyServiceHolder artsyServiceHolder) {
        this.context = context;
        this.artsyServiceHolder = artsyServiceHolder;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        if (artsyServiceHolder == null) {
            return null;
        }
        //Get the ClientId and Secret and exchange it for the Access Token
        String clientId = ArtsyToken.getDefaultClientId(context);
        String secret = ArtsyToken.getDefaultSecret(context);
        ArtsyToken request = new ArtsyToken();
        request.setClient_id(clientId);
        request.setClient_secret(secret);
        retrofit2.Response retrofitResponse = artsyServiceHolder.artsyService().getToken(request).execute();

        if (retrofitResponse != null) {
            ArtsyToken artsyToken = (ArtsyToken)retrofitResponse.body();

            return response.request().newBuilder()
                    .header("X-XAPP-Token", artsyToken.getToken())
                    .build();
        }

        return null;
    }
}