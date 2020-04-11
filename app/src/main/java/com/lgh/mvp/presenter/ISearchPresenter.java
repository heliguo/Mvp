package com.lgh.mvp.presenter;

import com.lgh.mvp.base.IBasePresenter;
import com.lgh.mvp.view.ISearchCallback;

public interface ISearchPresenter extends IBasePresenter<ISearchCallback> {

    /**
     * 获取搜索历史内容
     */
    void getHistoris();

    /**
     * 删除搜索历史内容
     */

    void deleteHistories();

    /**
     * 搜索
     */
    void doSearch(String keyword);

    /**
     * 重试搜索
     */
    void research();

    /**
     * 获取更多搜索结果
     */
    void loadMore();

    /**
     * 获取推荐词
     */
    void getRecommedWords();

}
