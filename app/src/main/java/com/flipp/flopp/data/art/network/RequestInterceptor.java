/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/1/19 10:49 PM
 *
 * Last modified 7/1/19 10:49 PM
 */

package com.flipp.flopp.data.art.network;

import android.content.Context;

import com.flipp.flopp.R;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {

    private Context context;
    public RequestInterceptor(Context context){
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalUrl = originalRequest.url();
        HttpUrl url = originalUrl.newBuilder()
                .addQueryParameter("api_key", context.getString(R.string.artsy_key))
                .build();

        Request.Builder requestBuilder = originalRequest.newBuilder().url(url);
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}