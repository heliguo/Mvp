package com.lgh.mvp.base;


/**
 * 加载数据基类
 *
 * @param <T>
 */
public interface IBasePresenter<T extends IBaseCallback> {

    void registerCallback(T callback);

    void unregisterCallback(T callback);
}
