package com.lgh.mvp.presenter;

import com.lgh.mvp.base.IBasePresenter;
import com.lgh.mvp.view.ITicketCallBack;

public interface ITicketPresenter extends IBasePresenter<ITicketCallBack> {

    void getTicket(String title,String url,String cover);
}
