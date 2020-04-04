package com.lgh.mvp.ui.activity;

import com.lgh.mvp.R;
import com.lgh.mvp.base.BaseActivity;
import com.lgh.mvp.presenter.impl.TicketPresenterImpl;

public class TicketActivity extends BaseActivity {

    private TicketPresenterImpl mTicketPresenter;

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ticket;
    }
}
