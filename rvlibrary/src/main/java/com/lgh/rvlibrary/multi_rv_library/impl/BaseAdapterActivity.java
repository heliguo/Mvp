package com.lgh.rvlibrary.multi_rv_library.impl;

import android.content.Context;
import android.os.Bundle;


import com.lgh.rvlibrary.R;
import com.lgh.rvlibrary.multi_rv_library.RViewHelper;
import com.lgh.rvlibrary.multi_rv_library.SwipeRefreshHelper;
import com.lgh.rvlibrary.multi_rv_library.core.RViewCreate;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


/**
 * author:lgh on 2019-11-14 11:32
 */
public abstract class BaseAdapterActivity extends AppCompatActivity implements RViewCreate, SwipeRefreshHelper.SwipeRefreshListener {

    protected RViewHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_adpter);
        helper = new RViewHelper.Builder(this, this).build();
    }


    @Override
    public Context context() {
        return this;
    }

    @Override
    public SwipeRefreshLayout createSwipeRefresh() {
        return findViewById(R.id.swiperefresh);
    }

    @Override
    public RecyclerView createRecyclerView() {
        return findViewById(R.id.recyclerview);
    }

    @Override
    public boolean isSupportPaging() {
        return false;
    }

    public void notifyAdapterDataSetChanged(List datas) {
        helper.notifyAdapterDataSetChanged(datas);
    }

}
