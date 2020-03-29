package com.lgh.mvp.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManaer {

    private static final RetrofitManaer ourInstance = new RetrofitManaer();

    private final Retrofit mRetrofit;

    public static RetrofitManaer getInstance() {
        return ourInstance;
    }

    private RetrofitManaer() {
        //创建Retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
