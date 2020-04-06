package com.lgh.mvp.presenter.impl;

import com.lgh.mvp.model.Api;
import com.lgh.mvp.model.domain.Categories;
import com.lgh.mvp.presenter.IHomePresenter;
import com.lgh.mvp.utils.LogUtils;
import com.lgh.mvp.utils.RetrofitManaer;
import com.lgh.mvp.view.IHomeCallback;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomePresenterImpl implements IHomePresenter {

    private IHomeCallback mCallback;

    @Override
    public void getCategories() {
        if (mCallback != null) {
            mCallback.onLoading();
        }
        Retrofit retrofit = RetrofitManaer.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        Call<Categories> categoriesCall = api.getCategories();
        categoriesCall.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                int code = response.code();
                LogUtils.e(HomePresenterImpl.this, String.valueOf(code));
                if (code == HttpURLConnection.HTTP_OK) {
                    Categories body = response.body();
                    if (mCallback != null) {
                        if (body == null || body.getData().size() == 0) {
                            mCallback.onEmpty();
                        }
                        mCallback.onLoadSuccess(body);
                    }
                } else {
                    if (mCallback != null) {
                        mCallback.onError();
                    }
                }

            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                if (mCallback != null) {
                    mCallback.onError();
                }
            }
        });
    }


    @Override
    public void registerCallback(IHomeCallback callback) {
        mCallback = callback;
    }

    @Override
    public void unregisterCallback(IHomeCallback callback) {
        mCallback = null;
    }
}
