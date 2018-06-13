package com.bing.lan.core.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiService {

    /**
     * 登录
     */
    @GET("user/login.do")
    Observable<Object> login(@QueryMap Map<String, String> map);
}
