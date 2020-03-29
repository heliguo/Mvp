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

public class HomePagerPresentImpl implements ICategoryPagerPresenter {

    private Map<Integer, Integer> pages = new HashMap<>();

    private static final int DEFAULT_PAGE = 1;

    private static HomePagerPresentImpl sInstance = null;

    public static HomePagerPresentImpl getInstance() {
        if (sInstance == null) {
            sInstance = new HomePagerPresentImpl();
        }
        return sInstance;
    }


    @Override
    public void getContentByCategoryId(int id) {
        for (ICategoryPagerCallback callback : mCallbacks) {
            if (callback.materialId() == id) {
                callback.onLoading(id);
            }
        }
        Retrofit retrofit = RetrofitManaer.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        Integer page = pages.get(id);
        if (page == null) {
            page = DEFAULT_PAGE;
        }
        String homePagerUrl = UrilUtils.createHomePagerUrl(id, page);
        Call<CategoryPager> categoryPager = api.getCategoryPager(homePagerUrl);
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

    private void handlerNetWorkError(int id) {
        for (ICategoryPagerCallback callback : mCallbacks) {
            if (callback.materialId() == id) {
                callback.onError(id);
            }

        }
    }

    private void handlerHomepagerContentResult(CategoryPager body, int id) {
        for (ICategoryPagerCallback callback : mCallbacks) {
            if (callback.materialId() == id) {
                if (body == null || body.getData().size() == 0) {
                    callback.onEmpty(id);
                } else {
                    callback.onCategorLoaded(body);
                }
            }
        }
    }

    @Override
    public void loadMore(int id) {

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
