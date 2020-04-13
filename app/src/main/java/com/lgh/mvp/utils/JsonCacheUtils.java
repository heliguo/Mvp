package com.lgh.mvp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.lgh.mvp.base.BaseApplication;
import com.lgh.mvp.model.domain.CacheWithDuration;

public class JsonCacheUtils {

    private static final String JSON_CACHE_SP_NAME = "json_cache_sp_name";

    private SharedPreferences sp;
    private Gson mGson;

    private JsonCacheUtils() {
        sp = BaseApplication.getAppContext().
                getSharedPreferences(JSON_CACHE_SP_NAME, Context.MODE_PRIVATE);
        mGson = new Gson();

    }

    private static JsonCacheUtils instance = null;

    public void saveCache(String key, Object value) {
        this.saveCache(key, value, -1L);
    }

    public void saveCache(String key, Object value, long duration) {
        SharedPreferences.Editor edit = sp.edit();
        String jsonValue = mGson.toJson(value);
        if (duration != -1L) {
            duration += System.currentTimeMillis();
        }
        CacheWithDuration cache = new CacheWithDuration(duration, jsonValue);
        String json = mGson.toJson(cache);
        edit.putString(key, json);
        edit.apply();
    }

    public void delCache(String key) {
        sp.edit().remove(key).apply();
    }

    public <T> T getValue(String key, Class<T> clazz) {

        String value = sp.getString(key, null);
        if (value == null) {
            return null;
        }
        CacheWithDuration cache = mGson.fromJson(value, CacheWithDuration.class);

        long duration = cache.getDuration();
        //是否过期
        long dTime = duration - System.currentTimeMillis();
        if (duration != -1 && dTime <= 0) {
            return null;
        } else {
            return mGson.fromJson(cache.getCache(), clazz);
        }

    }

    public static JsonCacheUtils getInstance() {
        if (instance == null) {
            instance = new JsonCacheUtils();
        }
        return instance;
    }
}
