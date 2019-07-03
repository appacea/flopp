/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.flipp.flopp.common.architecture;

import android.os.AsyncTask;

import java.io.IOException;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class NetworkBoundResource<ResultType, RequestType, RequestType2> {
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource() {
        result.setValue(Resource.loading(null));
        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> result.setValue(Resource.success(newData)));
            }
        });
    }

/*
    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        result.addSource(dbSource, newData -> result.setValue(Resource.loading(newData)));
        createCall().enqueue(new Callback<RequestType>() {
            @Override
            public void onResponse(Call<RequestType> call, Response<RequestType> response) {
                result.removeSource(dbSource);
                saveResultAndReInit(response.body());
            }

            @Override
            public void onFailure(Call<RequestType> call, Throwable t) {
                onFetchFailed();
                result.removeSource(dbSource);
                result.addSource(dbSource, newData -> result.setValue(Resource.error(t.getMessage(), newData)));
            }
        });
    }*/
/*
    @MainThread
    private void saveResultAndReInit(RequestType response) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                saveCallResult(response);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                result.addSource(loadFromDb(), newData -> result.setValue(Resource.success(newData)));
            }
        }.execute();
    }*/

    @MainThread
    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        result.addSource(dbSource, newData -> result.setValue(Resource.loading(newData)));
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Response responseOne = callOne().execute();
                    if (responseOne != null) {
                        Response responseTwo = callTwo((RequestType)responseOne.body()).execute();
                        saveCallResult((RequestType)responseOne.body(),(RequestType2)responseTwo.body());
                        return null;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                result.removeSource(dbSource);
                result.addSource(loadFromDb(), newData -> result.setValue(Resource.success(newData)));
            }
        }.execute();
    }


    @WorkerThread
    protected abstract Call<RequestType> callOne();

    @WorkerThread
    protected abstract Call<RequestType2> callTwo(@NonNull RequestType item);

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item,@NonNull RequestType2 item2);

    @MainThread
    protected boolean shouldFetch(@Nullable ResultType data) {
        return true;
    }

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

//    @NonNull
//    @MainThread
//    protected abstract Call<RequestType> createCall();
//
//
//    @NonNull
//    @MainThread
//    protected abstract Call<RequestType> createCall2();

    @MainThread
    protected void onFetchFailed() {
    }

    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return result;
    }
}