package com.lgh.mvp.model;

import com.lgh.mvp.model.domain.Categories;
import com.lgh.mvp.model.domain.CategoryPager;
import com.lgh.mvp.model.domain.SearchRecommend;
import com.lgh.mvp.model.domain.SearchResult;
import com.lgh.mvp.model.domain.TicketBeans;
import com.lgh.mvp.model.domain.TicketParams;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Api {

    @GET("discovery/categories")
    Call<Categories> getCategories();

    @GET
    Call<CategoryPager> getCategoryPager(@Url String url);

    @POST("tpwd")
    Call<TicketBeans> getTicket(@Body TicketParams params);

    @GET("search/recommend")
    Call<SearchRecommend> getRecommendWords();

    @GET("search")
    Call<SearchResult> getSearchResult(@Query("page") int page,
                                       @Query("keyword") String keyword);

}
