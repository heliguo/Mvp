package com.lgh.mvp.presenter.impl;

import com.lgh.mvp.model.Api;
import com.lgh.mvp.model.domain.TicketBeans;
import com.lgh.mvp.model.domain.TicketParams;
import com.lgh.mvp.presenter.ITicketPresenter;
import com.lgh.mvp.ui.dialog.DialogLoading;
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
    private DialogLoading mDialogLoading;

    @Override
    public void getTicket(String title, String url, String cover) {
        Retrofit retrofit = RetrofitManaer.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        String ticketUrl = UrilUtils.getTicketUrl(url);
        TicketParams params = new TicketParams(ticketUrl, title);
        Call<TicketBeans> ticket = api.getTicket(params);
        ticket.enqueue(new Callback<TicketBeans>() {
            @Override
            public void onResponse(Call<TicketBeans> call, Response<TicketBeans> response) {
                int code = response.code();

                LogUtils.e(TicketPresenterImpl.class, "code:  " + code);
                if (code == HttpURLConnection.HTTP_OK) {
                    TicketBeans ticketBeans = response.body();
                    if (ticketBeans != null)
                        mITicketCallBack.onItcketLoaded(cover, ticketBeans);
                } else {
                    handlerNetWorkError();
                }
            }

            @Override
            public void onFailure(Call<TicketBeans> call, Throwable t) {
                handlerNetWorkError();
            }
        });
    }

    @Override
    public void registerCallback(ITicketCallBack callback) {
        this.mITicketCallBack = callback;

    }

    @Override
    public void unregisterCallback(ITicketCallBack callback) {
        mITicketCallBack = null;
    }

    private void handlerNetWorkError() {
        mITicketCallBack.onError();
    }

}
