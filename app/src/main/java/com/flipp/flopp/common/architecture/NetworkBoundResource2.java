///*
// * Copyright (c) Tchipr Ltd 2019. All right reserved.
// * Unauthorized copying of this file, via any medium is strictly prohibited
// * Proprietary and confidential
// * Created by Yvan Stern on 7/4/19 4:05 PM
// *
// * Last modified 7/4/19 3:46 PM
// */
//
//package com.flipp.flopp.common.architecture;
//
//import android.os.AsyncTask;
//
//import androidx.annotation.MainThread;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.annotation.WorkerThread;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MediatorLiveData;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public abstract class NetworkBoundResource2<ResultType, RequestType, RequestType2> {
//    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();
//
//    @MainThread
//    public NetworkBoundResource2() {
//        result.setValue(Resource.loading(null));
//        LiveData<ResultType> dbSource = loadFromDb();
//        result.addSource(dbSource, data -> {
//            result.removeSource(dbSource);
//            if (shouldFetch(data)) {
//                fetchFromNetwork(dbSource);
//            } else {
//                result.addSource(dbSource, newData -> result.setValue(Resource.success(newData)));
//            }
//        });
//    }
//    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
//        result.addSource(dbSource, newData -> result.setValue(Resource.loading(newData)));
//        callOne().enqueue(new Callback<RequestType>() {
//            @Override
//            public void onResponse(Call<RequestType> call, Response<RequestType> response) {
//                //result.removeSource(dbSource);
//                //saveResultAndReInit(response.body());
//                callTwoExec(dbSource, response.body());
//            }
//
//            @Override
//            public void onFailure(Call<RequestType> call, Throwable t) {
//                onFetchFailed();
//                result.removeSource(dbSource);
//                result.addSource(dbSource, newData -> result.setValue(Resource.error(t.getMessage(), newData)));
//            }
//        });
//    }
//
//    private void callTwoExec(final LiveData<ResultType> dbSource, RequestType requestOneType){
//        callTwo(requestOneType).enqueue(new Callback<RequestType2>() {
//            @Override
//            public void onResponse(Call<RequestType2> call, Response<RequestType2> response) {
//                result.removeSource(dbSource);
//                saveResultAndReInit(requestOneType,(RequestType2)response.body());
//                //saveCallResult(requestOneType,(RequestType2)response.body());
//
//            }
//
//            @Override
//            public void onFailure(Call<RequestType2> call, Throwable t) {
//                onFetchFailed();
//                result.removeSource(dbSource);
//                result.addSource(dbSource, newData -> result.setValue(Resource.error(t.getMessage(), newData)));
//            }
//        });
//    }
//
//
//    @MainThread
//    private void saveResultAndReInit(RequestType item,RequestType2 item2) {
//        new AsyncTask<Void, Void, Void>() {
//
//            @Override
//            protected Void doInBackground(Void... voids) {
//                saveCallResult(item,item2);
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                result.addSource(loadFromDb(), newData -> result.setValue(Resource.success(newData)));
//            }
//        }.execute();
//    }
///*
//    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
//        result.addSource(dbSource, newData -> result.setValue(Resource.loading(newData)));
//        createCall().enqueue(new Callback<RequestType>() {
//            @Override
//            public void onResponse(Call<RequestType> call, Response<RequestType> response) {
//                result.removeSource(dbSource);
//                saveResultAndReInit(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<RequestType> call, Throwable t) {
//                onFetchFailed();
//                result.removeSource(dbSource);
//                result.addSource(dbSource, newData -> result.setValue(Resource.error(t.getMessage(), newData)));
//            }
//        });
//    }*/
///*
//    @MainThread
//    private void saveResultAndReInit(RequestType response) {
//        new AsyncTask<Void, Void, Void>() {
//
//            @Override
//            protected Void doInBackground(Void... voids) {
//                saveCallResult(response);
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                result.addSource(loadFromDb(), newData -> result.setValue(Resource.success(newData)));
//            }
//        }.execute();
//    }*/
///*
//    @MainThread
//    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
//        result.addSource(dbSource, newData -> result.setValue(Resource.loading(newData)));
//        new AsyncTask<Void, Void, Void>() {
//
//            @Override
//            protected Void doInBackground(Void... voids) {
//                try {
//                    Response responseOne = callOne().execute();
//                    if (responseOne != null) {
//                        Response responseTwo = callTwo((RequestType)responseOne.body()).execute();
//                        saveCallResult((RequestType)responseOne.body(),(RequestType2)responseTwo.body());
//                        return null;
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//
//                result.removeSource(dbSource);
//                result.addSource(loadFromDb(), newData -> result.setValue(Resource.success(newData)));
//            }
//        }.execute();
//    }
//*/
//
//    @WorkerThread
//    protected abstract Call<RequestType> callOne();
//
//    @WorkerThread
//    protected abstract Call<RequestType2> callTwo(@NonNull RequestType item);
//
//    @WorkerThread
//    protected abstract void saveCallResult(@NonNull RequestType item,@NonNull RequestType2 item2);
//
//    @MainThread
//    protected boolean shouldFetch(@Nullable ResultType data) {
//        return true;
//    } //Always fetch since this is just a demo
//
//    @NonNull
//    @MainThread
//    protected abstract LiveData<ResultType> loadFromDb();
//
////    @NonNull
////    @MainThread
////    protected abstract Call<RequestType> createCall();
////
////
////    @NonNull
////    @MainThread
////    protected abstract Call<RequestType> createCall2();
//
//    @MainThread
//    protected void onFetchFailed() {
//    }
//
//    public final LiveData<Resource<ResultType>> getAsLiveData() {
//        return result;
//    }
//}