package com.bing.lan.core.api;

import java.io.InputStream;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class ApiManager extends BaseApiManager {

    public static ApiManager instance = new ApiManager();
    //===================================================
    public Retrofit mApiRetrofit;
    private ApiService mApiService;

    private ApiManager() {
    }

    public static ApiManager getInstance() {
        return instance;
    }

    public ApiService getApiService() {
        if (mApiService == null) {
            initJzkApiService();
        }
        return mApiService;
    }

    private void initJzkApiService() {
        if (mApiRetrofit == null) {
            mApiRetrofit = getRetrofit("http://localhost:8085/api/");
        }
        mApiService = mApiRetrofit.create(ApiService.class);
    }
    //===================================================

    @Override
    protected void setInterceptor(OkHttpClient.Builder builder) {
        // 设置公共参数
        //builder.addInterceptor(new CommParaInterceptor());

        // 添加进度显示
        //builder.addInterceptor(new AddProgressInterceptor());
    }

    @Override
    protected InputStream getCertificatesInputStream() {
        InputStream inputStream = null;
        //try {
        //    //InputStream inputStream = AppUtil.openRawResource(R.raw.jzk);  // 证书
        //    if (BuildConfig.BUILD_TYPES == MyBuildType.BUILD_TYPE_DEBUG) {
        //        inputStream = null;
        //    } else if (BuildConfig.BUILD_TYPES == MyBuildType.BUILD_TYPE_DEBUG_PRE) {
        //        inputStream = AppUtil.getAssets().open("jzkgdysj.pem");
        //    } else if (BuildConfig.BUILD_TYPES == MyBuildType.BUILD_TYPE_RELEASE) {
        //        inputStream = AppUtil.getAssets().open("jzk.pem");
        //    }
        //} catch (IOException e) {
        //    log.e("getCertificatesInputStream(): 加载证书异常 " + e.getLocalizedMessage());
        //    inputStream = null;
        //}

        return inputStream;
    }
}
