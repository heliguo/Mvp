package com.lgh.mvp.base;


/**
 * view获取数据基类
 *
 * @param <T>
 */
public interface IBaseCallback<T extends BaseBean> {

    /**
     * 加载成功
     *
     * @param t
     */
    void onCategorLoaded(T t);

    /**
     * 网络加载失败
     */
    void onError(Object... objects);

    /**
     * 正在加载
     */
    void onLoading(Object... objects);

    /**
     * 数据为空
     */
    void onEmpty(Object... objects);

}
