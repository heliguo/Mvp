package com.lgh.mvp.presenter.impl;

import com.lgh.mvp.model.Api;
import com.lgh.mvp.model.domain.Histories;
import com.lgh.mvp.model.domain.SearchRecommend;
import com.lgh.mvp.model.domain.SearchResult;
import com.lgh.mvp.presenter.ISearchPresenter;
import com.lgh.mvp.utils.JsonCacheUtils;
import com.lgh.mvp.utils.RetrofitManaer;
import com.lgh.mvp.view.ISearchCallback;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchPresenterImpl implements ISearchPresenter {

    private final Api mApi;
    private ISearchCallback mISearchCallback;
    private JsonCacheUtils instance;

    public SearchPresenterImpl() {
        Retrofit retrofit = RetrofitManaer.getInstance().getRetrofit();
        mApi = retrofit.create(Api.class);
        instance = JsonCacheUtils.getInstance();
    }

    public static final String KEY_HISTORIES = "key_histories";
    public static final int DEFAULT_HISTORIES_SIZE = 10;
    private int historyMaxSize = DEFAULT_HISTORIES_SIZE;

    @Override
    public void saveHistories(String history) {
        Histories histories = instance.getValue(KEY_HISTORIES, Histories.class);
        List<String> historyList = null;

        //存在删除
        if (histories != null && histories.getHistories() != null) {
            historyList = histories.getHistories();
            if (historyList.contains(history)) {
                historyList.remove(history);
            }
        }

        if (historyList == null) {
            historyList = new ArrayList<>();
        }
        if (histories == null) {
            histories = new Histories();
        }

        historyList.add(history);

        if (historyList.size() > historyMaxSize) {
            historyList = historyList.subList(0, historyMaxSize);
        }

        histories.setHistories(historyList);

        instance.saveCache(KEY_HISTORIES, histories);
    }

    @Override
    public void getHistoris() {
        Histories value = instance.getValue(KEY_HISTORIES, Histories.class);
        if (mISearchCallback != null) {
            if (!isHistoriesEmpty(value)) {
                mISearchCallback.onHistoriesLoaded(value.getHistories());
            }
        }


    }

    private boolean isHistoriesEmpty(Histories value) {
        return value == null || value.getHistories() == null || value.getHistories().size() == 0;
    }

    @Override
    public void deleteHistories() {
        instance.delCache(KEY_HISTORIES);
    }

    private static final int DEFAULT_PAGE = 0;

    private int mCurrentPage = DEFAULT_PAGE;

    private String mKeyword;

    @Override
    public void doSearch(String keyword) {
        this.mKeyword = keyword;
        if (mISearchCallback != null) {
            mISearchCallback.onLoading();
        }
        Call<SearchResult> searchResult = mApi.getSearchResult(mCurrentPage, keyword);
        searchResult.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    handlerSearchResult(response.body());
                } else {
                    handlerSearchError();
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                handlerSearchError();
            }
        });

    }

    private void handlerSearchResult(SearchResult body) {
        if (mISearchCallback != null) {
            if (isResultEmpty(body)) {
                mISearchCallback.onEmpty();
            } else {
                mISearchCallback.onLoadSuccess(body);
            }
        }


    }

    private boolean isResultEmpty(SearchResult body) {
        try {
            return body == null || body.getData().getTbk_dg_material_optional_response().
                    getResult_list().getMap_data().size() == 0;
        } catch (Exception e) {
            return false;
        }
    }

    private void handlerSearchError() {
        if (mISearchCallback != null) {
            mISearchCallback.onError();
        }
    }

    @Override
    public void research() {
        if (mKeyword == null) {
            if (mISearchCallback != null) {
                mISearchCallback.onEmpty();
            }
        } else {
            this.doSearch(mKeyword);
        }

    }

    @Override
    public void loadMore() {
        mCurrentPage++;
        if (mKeyword == null) {
            if (mISearchCallback != null) {
                mISearchCallback.onEmpty();
            }
        } else {
            this.doSearch(mKeyword);
        }

    }

    @Override
    public void getRecommedWords() {
        Call<SearchRecommend> task = mApi.getRecommendWords();
        task.enqueue(new Callback<SearchRecommend>() {
            @Override
            public void onResponse(Call<SearchRecommend> call, Response<SearchRecommend> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    if (mISearchCallback != null) {
                        mISearchCallback.onRecommendWordsLoaded(response.body().getData());
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchRecommend> call, Throwable t) {

            }
        });
    }

    @Override
    public void registerCallback(ISearchCallback callback) {
        this.mISearchCallback = callback;

    }

    @Override
    public void unregisterCallback(ISearchCallback callback) {
        mISearchCallback = null;
    }
}
