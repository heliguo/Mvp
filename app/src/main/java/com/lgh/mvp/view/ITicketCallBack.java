package com.lgh.mvp.view;

import com.lgh.mvp.base.IBaseCallback;
import com.lgh.mvp.model.domain.TicketBeans;

public class ITicketCallBack implements IBaseCallback<TicketBeans> {

    @Override
    public void onCategorLoaded(TicketBeans ticketBeans) {

    }

    @Override
    public void onError(Object... objects) {

    }

    @Override
    public void onLoading(Object... objects) {

    }

    @Override
    public void onEmpty(Object... objects) {

    }
}
