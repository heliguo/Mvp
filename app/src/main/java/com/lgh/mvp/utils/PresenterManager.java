package com.lgh.mvp.utils;

import com.lgh.mvp.presenter.impl.HomePagerPresentImpl;
import com.lgh.mvp.presenter.impl.HomePresenterImpl;
import com.lgh.mvp.presenter.impl.TicketPresenterImpl;

public class PresenterManager {

    private static final PresenterManager ourInstance = new PresenterManager();
    private HomePagerPresentImpl mHomePagerPresent;
    private HomePresenterImpl mHomePresenter;
    private TicketPresenterImpl mTicketPresenter;

    public static PresenterManager getInstance() {
        return ourInstance;
    }

    private PresenterManager() {
        mHomePagerPresent = new HomePagerPresentImpl();
        mHomePresenter = new HomePresenterImpl();
        mTicketPresenter = new TicketPresenterImpl();

    }

    public HomePagerPresentImpl getHomePagerPresent() {
        return mHomePagerPresent;
    }

    public HomePresenterImpl getHomePresenter() {
        return mHomePresenter;
    }

    public TicketPresenterImpl getTicketPresenter() {
        return mTicketPresenter;
    }
}
