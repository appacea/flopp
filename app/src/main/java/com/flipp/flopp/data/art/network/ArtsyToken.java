/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/1/19 11:01 PM
 *
 * Last modified 7/1/19 11:01 PM
 */

package com.flipp.flopp.data.art.network;

import android.content.Context;

/***
 * Class to store and receive Artsy Authentication tokens
 */
public class ArtsyToken {

    private String token;
    private String client_id;
    private String client_secret;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public static String getDefaultClientId(Context context){
        return "68eac45aa54597af1a96";
    }

    public static String getDefaultSecret(Context context){
        return "5051b4e933ae50d39bafc35079bc5d93";
    }
}
