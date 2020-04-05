package com.lgh.mvp.view;

import com.lgh.mvp.base.IBaseCallback;
import com.lgh.mvp.model.domain.TicketBeans;
import com.lgh.mvp.model.domain.TicketParams;

public interface ITicketCallBack extends IBaseCallback<TicketBeans> {

    void onItcketLoaded(String cover, TicketBeans params);
}
