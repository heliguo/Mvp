package com.lgh.rvlibrary.multi_rv_library;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class SwipeRefreshHelper {

    private SwipeRefreshLayout swipeRefreshLayout;

    private SwipeRefreshListener swipeRefreshListener;

    static SwipeRefreshHelper createSwipeRefreshHelper(SwipeRefreshLayout swipeRefreshLayout) {
        return new SwipeRefreshHelper(swipeRefreshLayout);
    }

    public SwipeRefreshHelper(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
        init();
    }

    private void init() {
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark,
                android.R.color.holo_green_dark, android.R.color.holo_blue_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (swipeRefreshListener != null) {
                    swipeRefreshListener.OnRefresh();
                }
            }
        });
    }

    public interface SwipeRefreshListener {
        void OnRefresh();
    }

    void setSwipeRefreshListener(SwipeRefreshListener swipeRefreshListener) {
        this.swipeRefreshListener = swipeRefreshListener;
    }
}
