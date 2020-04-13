package com.lgh.mvp.view;

import com.lgh.mvp.base.IBaseCallback;
import com.lgh.mvp.model.domain.SearchRecommend;
import com.lgh.mvp.model.domain.SearchResult;

import java.util.List;

public interface ISearchCallback extends IBaseCallback<SearchResult> {

    /**
     * 获取搜索历史
     *
     * @param histories
     */
    void onHistoriesLoaded(List<String> histories);

    /**
     * 历史删除成功，更新UI
     */
    void onHistoriesDeleted();

    /**
     * 获取推荐词成功
     *
     * @param recommend
     */
    void onRecommendWordsLoaded(List<SearchRecommend.DataBean > recommend);
}
