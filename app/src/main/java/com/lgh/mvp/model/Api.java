package com.lgh.mvp.model;

import com.lgh.mvp.model.domain.Categories;
import com.lgh.mvp.model.domain.CategoryPager;
import com.lgh.mvp.model.domain.TicketBeans;
import com.lgh.mvp.model.domain.TicketParams;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface Api {

    @GET("discovery/categories")
    Call<Categories> getCategories();

    @GET
    Call<CategoryPager> getCategoryPager(@Url String url);

    @POST("tpwd")
    Call<TicketBeans> getTicket(@Body TicketParams params);

}
