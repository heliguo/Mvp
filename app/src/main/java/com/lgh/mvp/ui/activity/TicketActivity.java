package com.lgh.mvp.ui.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lgh.mvp.R;
import com.lgh.mvp.base.BaseActivity;
import com.lgh.mvp.model.domain.TicketBeans;
import com.lgh.mvp.presenter.impl.TicketPresenterImpl;
import com.lgh.mvp.ui.custom.loadview.PageLayout;
import com.lgh.mvp.utils.LogUtils;
import com.lgh.mvp.utils.PresenterManager;
import com.lgh.mvp.view.ITicketCallBack;

public class TicketActivity extends BaseActivity implements ITicketCallBack {

    private TicketPresenterImpl mTicketPresenter;
    private View rootView;
    public PageLayout.Builder mBuilder;
    public PageLayout mPageLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.base_fragment_layout;
    }

    @Override
    protected void initPresenter() {
        mTicketPresenter = PresenterManager.getInstance().getTicketPresenter();
        if (mTicketPresenter != null) {
            mTicketPresenter.registerCallback(this);
        }

    }

    @Override
    protected void initView() {
        mBuilder = new PageLayout.Builder(this);
        mBuilder.initPage(this);
        mBuilder.setLoading(R.layout.layout_loading_anim, R.id.tv_page_loading_custom);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_ticket, null);
        mBuilder.setCustomView(rootView);
        mPageLayout = mBuilder.create();
    }


    @Override
    public void onLoadSuccess(TicketBeans ticketBeans, Object... objects) {
        mPageLayout.showCustom();
        EditText ev = rootView.findViewById(R.id.ticket_tao_kl);
        ImageView iv = rootView.findViewById(R.id.ticket_pic);
        Button btn = rootView.findViewById(R.id.ticket_btn);
        if (ev != null && iv != null && btn != null) {
            String cover = "http:" + (String) objects[0];
            Glide.with(TicketActivity.this).load(cover).into(iv);
            ev.setText("￥xhQoYC66sMX￥");
            btn.setOnClickListener(v -> {
                LogUtils.e(this, "淘口令:  " + ev.getText().toString());
            });
        }
    }

    @Override
    public void onError(Object... objects) {
        mPageLayout.showError();
        String title = (String) objects[0];
        String url = (String) objects[1];
        String cover = (String) objects[2];
        mBuilder.setOnRetryListener(() -> {
            mTicketPresenter.getTicket(title, url, cover);
        });

    }

    @Override
    public void onLoading(Object... objects) {
        mPageLayout.showLoading();
    }

    @Override
    public void onEmpty(Object... objects) {
        mPageLayout.showEmpty();
    }

    @Override
    protected void release() {
        if (mTicketPresenter != null) {
            mTicketPresenter.unregisterCallback(this);
        }
    }

}
