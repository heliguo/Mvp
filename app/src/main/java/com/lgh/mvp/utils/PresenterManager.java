package com.lgh.mvp.utils;

import com.lgh.mvp.presenter.impl.HomePagerPresenterImpl;
import com.lgh.mvp.presenter.impl.HomePresenterImpl;
import com.lgh.mvp.presenter.impl.SearchPresenterImpl;
import com.lgh.mvp.presenter.impl.TicketPresenterImpl;

public class PresenterManager {

    private static final PresenterManager ourInstance = new PresenterManager();
    private HomePagerPresenterImpl mHomePagerPresent;
    private HomePresenterImpl mHomePresenter;
    private TicketPresenterImpl mTicketPresenter;
    private SearchPresenterImpl mSearchPresenter;

    public static PresenterManager getInstance() {
        return ourInstance;
    }

    private PresenterManager() {
        if (mHomePagerPresent == null) {
            mHomePagerPresent = new HomePagerPresenterImpl();
        }
        if (mHomePresenter == null) {
            mHomePresenter = new HomePresenterImpl();
        }

        if (mTicketPresenter == null) {
            mTicketPresenter = new TicketPresenterImpl();
        }
        if (mSearchPresenter==null){
            mSearchPresenter = new SearchPresenterImpl();
        }
    }

    public HomePagerPresenterImpl getHomePagerPresent() {
        return mHomePagerPresent;
    }

    public HomePresenterImpl getHomePresenter() {
        return mHomePresenter;
    }

    public TicketPresenterImpl getTicketPresenter() {
        return mTicketPresenter;
    }

    public SearchPresenterImpl getSearchPresenter() {
        return mSearchPresenter;
    }
}
