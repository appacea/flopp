/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/4/19 4:47 PM
 *
 * Last modified 7/4/19 4:47 PM
 */

package com.flipp.flopp;

import com.flipp.flopp.common.architecture.ApiResponse;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Response;


public class ApiUtil {
    public static <T>  LiveData<ApiResponse<T>> successCall(T data) {
        return createCall(Response.success(data));
    }
    public static <T> LiveData<ApiResponse<T>> createCall(Response<T> response) {
        MutableLiveData<ApiResponse<T>> data = new MutableLiveData<>();
        data.setValue(new ApiResponse<>(response));
        return data;
    }
}