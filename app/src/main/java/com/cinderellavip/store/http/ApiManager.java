package com.cinderellavip.store.http;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 */
public class ApiManager {

    private static ApiManager mInstance;
    private ApiService mApiService;
    public ApiManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUrl.server_url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpUtils.getInstance())
                .build();
        mApiService = retrofit.create(ApiService.class);
    }
    //单例模式
    public static ApiService getService() {
        if (mInstance == null) {
            mInstance = new ApiManager();
        }
        return mInstance.mApiService;
    }





}
