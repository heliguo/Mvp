package com.lgh.mvp.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lgh.mvp.R;
import com.lgh.mvp.base.BaseActivity;
import com.lgh.mvp.model.domain.TicketBeans;
import com.lgh.mvp.presenter.impl.TicketPresenterImpl;
import com.lgh.mvp.ui.custom.loadview.PageLayout;
import com.lgh.mvp.utils.PresenterManager;
import com.lgh.mvp.view.ITicketCallBack;

public class TicketActivity extends BaseActivity implements ITicketCallBack {

    private TicketPresenterImpl mTicketPresenter;
    private View rootView;
    public PageLayout.Builder mBuilder;
    public PageLayout mPageLayout;
    private boolean hasTaoBao;
    private Button btn;

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

        /**
         * 判断是否安装淘宝
         * adb shell
         * logcat | grep START
         */
        // {act=android.intent.action.VIEW flg=0x4000000 hwFlg=0x10
        // pkg=com.taobao.taobao
        // cmp=com.taobao.taobao/com.taobao.tao.TBMainActivity (has extras)}
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo("com.taobao.taobao",
                    PackageManager.MATCH_UNINSTALLED_PACKAGES);
            hasTaoBao = packageInfo != null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            hasTaoBao = false;
        }

        EditText ev = rootView.findViewById(R.id.ticket_tao_kl);
        ImageView iv = rootView.findViewById(R.id.ticket_pic);
        btn = rootView.findViewById(R.id.ticket_btn);

        btn.setText(hasTaoBao ? "打开淘宝领券" : "复制口令");
        String cover = "http:" + (String) objects[0];
        Glide.with(TicketActivity.this).load(cover).into(iv);
        ev.setText("￥xhQoYC66sMX￥");
        btn.setOnClickListener(v -> {
            String ticketCode = ev.getText().toString().trim();
            //复制到剪切板
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("taobao_ticket_code", ticketCode);
            cm.setPrimaryClip(clipData);
            //有淘宝打开，没有提示
            if (hasTaoBao) {
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName("com.taobao.taobao",
                        "com.taobao.tao.TBMainActivity");
                intent.setComponent(componentName);
                startActivity(intent);
            } else {
                Toast.makeText(TicketActivity.this, "口令复制成功", Toast.LENGTH_SHORT).show();
            }

        });
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
