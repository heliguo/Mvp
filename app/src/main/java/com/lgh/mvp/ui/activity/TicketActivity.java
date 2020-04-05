package com.lgh.mvp.ui.activity;

import com.lgh.mvp.R;
import com.lgh.mvp.base.BaseActivity;
import com.lgh.mvp.model.domain.TicketBeans;
import com.lgh.mvp.presenter.impl.TicketPresenterImpl;
import com.lgh.mvp.utils.PresenterManager;
import com.lgh.mvp.view.ITicketCallBack;

public class TicketActivity extends BaseActivity implements ITicketCallBack {

    private TicketPresenterImpl mTicketPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ticket;
    }

    @Override
    protected void initPresenter() {
        mTicketPresenter = PresenterManager.getInstance().getTicketPresenter();
        if (mTicketPresenter != null) {
            mTicketPresenter.registerCallback(this);
        }

    }

    @Override
    public void onItcketLoaded(String cover, TicketBeans params) {

    }

    @Override
    protected void initView() {

    }


    @Override
    public void onCategorLoaded(TicketBeans ticketBeans) {

    }

    @Override
    public void onError(Object... objects) {

    }

    @Override
    public void onLoading(Object... objects) {

    }

    @Override
    public void onEmpty(Object... objects) {

    }

    @Override
    protected void release() {
        if (mTicketPresenter != null) {
            mTicketPresenter.unregisterCallback(this);
        }
    }


}
