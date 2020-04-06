package com.lgh.mvp.presenter.impl;

import android.opengl.GLES10;
import android.util.Log;

import com.lgh.mvp.model.Api;
import com.lgh.mvp.model.domain.TicketBeans;
import com.lgh.mvp.model.domain.TicketParams;
import com.lgh.mvp.presenter.ITicketPresenter;
import com.lgh.mvp.utils.LogUtils;
import com.lgh.mvp.utils.RetrofitManaer;
import com.lgh.mvp.utils.UrilUtils;
import com.lgh.mvp.view.ITicketCallBack;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TicketPresenterImpl implements ITicketPresenter {

    private ITicketCallBack mITicketCallBack;

    enum TicketState {
        NONE,
        LOADING,
        SUCCESS,
        ERROR
    }

    private TicketState currentState = TicketState.NONE;
    private TicketBeans ticketBeans;
    private String mCover;
    private String mTitle;
    private String mUrl;


    @Override
    public void getTicket(String title, String url, String cover) {
        currentState = TicketState.LOADING;
        this.handlerLoading();
        this.mCover = cover;
        this.mTitle = title;
        this.mUrl = url;
        if (mITicketCallBack != null)
            mITicketCallBack.onLoading();
        Retrofit retrofit = RetrofitManaer.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        String ticketUrl = UrilUtils.getTicketUrl(url);
        TicketParams params = new TicketParams(ticketUrl, title);
        Call<TicketBeans> ticket = api.getTicket(params);
        ticket.enqueue(new Callback<TicketBeans>() {
            @Override
            public void onResponse(Call<TicketBeans> call, Response<TicketBeans> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    currentState = TicketState.SUCCESS;
                    ticketBeans = response.body();
                    handlerLoadSuccess();
                } else {
                    handlerNetWorkError(title, url, cover);
                }
            }

            @Override
            public void onFailure(Call<TicketBeans> call, Throwable t) {
                handlerNetWorkError(title, url, cover);
            }
        });
    }

    private void handlerLoadSuccess() {
        if (mITicketCallBack != null) {
            if (ticketBeans != null) {
                mITicketCallBack.onLoadSuccess(ticketBeans, mCover);
            } else {
                mITicketCallBack.onEmpty();
            }
        }
    }

    private void handlerLoading() {
        if (mITicketCallBack != null) {
            mITicketCallBack.onLoading();
        }
    }

    @Override
    public void registerCallback(ITicketCallBack callback) {
        this.mITicketCallBack = callback;
        if (currentState != TicketState.NONE) {
            //状态已经改变，可以更新UI
            if (currentState == TicketState.SUCCESS) {
                handlerLoadSuccess();
            } else if (currentState == TicketState.ERROR) {
                handlerNetWorkError(mTitle, mUrl, mCover);
            } else if (currentState == TicketState.LOADING) {
                handlerLoading();
            }
        }

    }

    @Override
    public void unregisterCallback(ITicketCallBack callback) {
        mITicketCallBack = null;
    }

    private void handlerNetWorkError(String title, String url, String cover) {
        currentState = TicketState.ERROR;
        if (mITicketCallBack != null)
            mITicketCallBack.onError(title, url, cover);
    }


}
