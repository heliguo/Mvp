package com.lgh.mvp.ui.dialog;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.lgh.mvp.utils.Constants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingHandler extends Handler {

    private com.lgh.mvp.ui.dialog.ILoadingDialog ILoadingDialog;
    private AppCompatActivity mActivity;

    public LoadingHandler(Looper looper, ILoadingDialog ILoadingDialog, AppCompatActivity activity) {
        super(looper);
        this.ILoadingDialog = ILoadingDialog;
        mActivity = activity;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        switch (msg.what) {
            case Constants.HANDLER_DIALOG_SHOW:
                if (ILoadingDialog != null && mActivity != null)
                    ILoadingDialog.show(mActivity);
                break;
            case Constants.HANDLER_DIALOG_DISMISS:
                if (ILoadingDialog != null && mActivity != null)
                    ILoadingDialog.dismiss();
                break;
        }
    }
}
