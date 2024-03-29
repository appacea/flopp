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



import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 * @param <ResultType>
 * @param <RequestType>
 */
public abstract class NetworkBoundResource<ResultType, RequestType, RequestType2> {
    private final AppExecutors appExecutors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
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

    private  void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();


        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource, newData -> result.setValue(Resource.loading(newData)));
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
          //  result.removeSource(dbSource);
            //noinspection ConstantConditions
            if (response.isSuccessful()) {
                LiveData<ApiResponse<RequestType2>> apiResponse2 = createCall2();
                result.addSource(apiResponse2, response2 -> {
                            result.removeSource(apiResponse2);
                            result.removeSource(dbSource);
                            if (response2.isSuccessful()) {
                                appExecutors.diskIO().execute(() -> {
                                    saveCallResult(response.body,response2.body);
                                    appExecutors.mainThread().execute(() ->
                                            // we specially request a new live data,
                                            // otherwise we will get immediately last cached value,
                                            // which may not be updated with latest results received from network.
                                            result.addSource(loadFromDb(),
                                                    newData -> result.setValue(Resource.success(newData)))
                                    );
                                });
                            }
                            else{
                                onFetchFailed();
                                result.addSource(dbSource,
                                        newData -> result.setValue(Resource.error(response.errorMessage, newData)));
                            }
                        });

            } else {
                onFetchFailed();
                result.addSource(dbSource,
                        newData -> result.setValue(Resource.error(response.errorMessage, newData)));
            }
        });
    }

    protected  void onFetchFailed() {
    }

    public  LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }


    @WorkerThread
    protected abstract  void saveCallResult(@NonNull RequestType item,@NonNull RequestType2 item2);


    @MainThread
    protected abstract  boolean shouldFetch(@Nullable ResultType data);

    @NonNull
    @MainThread
    protected abstract  LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract  LiveData<ApiResponse<RequestType>> createCall();

    @NonNull
    @MainThread
    protected abstract  LiveData<ApiResponse<RequestType2>> createCall2();
}