package com.lgh.mvp.presenter.impl;

import com.lgh.mvp.model.Api;
import com.lgh.mvp.model.domain.CategoryPager;
import com.lgh.mvp.presenter.ICategoryPagerPresenter;
import com.lgh.mvp.utils.RetrofitManaer;
import com.lgh.mvp.utils.UrilUtils;
import com.lgh.mvp.view.ICategoryPagerCallback;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomePagerPresenterImpl implements ICategoryPagerPresenter {

    private Map<Integer, Integer> pages = new HashMap<>();

    private static final int DEFAULT_PAGE = 1;

    private Integer currentPage;//当前页


    @Override
    public void getContentByCategoryId(int id) {
        for (ICategoryPagerCallback callback : mCallbacks) {
            if (callback.materialId() == id) {
                callback.onLoading(id);
            }
        }
        currentPage = pages.get(id);
        if (currentPage == null) {
            currentPage = DEFAULT_PAGE;
        }
        Call<CategoryPager> categoryPager = createTask(id, currentPage);
        categoryPager.enqueue(new Callback<CategoryPager>() {
            @Override
            public void onResponse(Call<CategoryPager> call, Response<CategoryPager> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    CategoryPager body = response.body();
                    handlerHomepagerContentResult(body, id);
                } else {
                    handlerNetWorkError(id);
                }
            }

            @Override
            public void onFailure(Call<CategoryPager> call, Throwable t) {
                handlerNetWorkError(id);
            }
        });
    }

    private Call<CategoryPager> createTask(int id, Integer page) {
        Retrofit retrofit = RetrofitManaer.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        String homePagerUrl = UrilUtils.createHomePagerUrl(id, page);
        return api.getCategoryPager(homePagerUrl);
    }

    private void handlerNetWorkError(int id) {
        for (ICategoryPagerCallback callback : mCallbacks) {
            if (callback.materialId() == id) {
                callback.onError(id);
            }
        }
    }

    private void handlerHomepagerContentResult(CategoryPager body, int id) {
        List<CategoryPager.DataBean> data = body.getData();
        for (ICategoryPagerCallback callback : mCallbacks) {
            if (callback.materialId() == id) {
                if (body == null || body.getData().size() == 0) {
                    callback.onEmpty(id);
                } else {
                    List<CategoryPager.DataBean> dataBeans = data.subList(data.size() - 5, data.size());
                    callback.onLooperListLoaded(dataBeans, id);
                    callback.onLoadSuccess(body);
                    pages.put(id, currentPage);
                }
            }
        }
    }

    @Override
    public void loadMore(int id) {
        //当前页、增加页码、加载数据、处理数据结果
        currentPage = pages.get(id);
        if (currentPage == null) {
            return;
        }
        currentPage++;
        Call<CategoryPager> categoryPager = createTask(id, currentPage);
        categoryPager.enqueue(new Callback<CategoryPager>() {
            @Override
            public void onResponse(Call<CategoryPager> call, Response<CategoryPager> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    handlerNetWorkLoadMoreResult(response.body(), id);

                } else {
                    handlerNetWorkLoadMoreError(id);
                }
            }

            @Override
            public void onFailure(Call<CategoryPager> call, Throwable t) {
                handlerNetWorkLoadMoreError(id);
            }
        });
    }

    private void handlerNetWorkLoadMoreResult(CategoryPager body, int id) {
        for (ICategoryPagerCallback callback : mCallbacks) {
            if (callback.materialId() == id) {
                if (body == null || body.getData().size() == 0) {
                    callback.onLoaderMoreEmpty(id);
                    currentPage--;
                } else {
                    callback.onLoaderMoreLoading(body.getData(), id);
                    pages.put(id, currentPage);
                }
            }
        }
    }


    private void handlerNetWorkLoadMoreError(int id) {
        currentPage--;
        for (ICategoryPagerCallback callback : mCallbacks) {
            if (callback.materialId() == id) {
                callback.onLoaderMoreError(id);
            }
        }
    }

    @Override
    public void reload(int id) {

    }


    private List<ICategoryPagerCallback> mCallbacks = new ArrayList<>();

    @Override
    public void registerCallback(ICategoryPagerCallback callback) {
        if (!mCallbacks.contains(callback)) {
            mCallbacks.add(callback);
        }
    }

    @Override
    public void unregisterCallback(ICategoryPagerCallback callback) {
        if (mCallbacks.contains(callback)) {
            mCallbacks.remove(callback);
        }
    }
}
