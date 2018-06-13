package com.bing.lan.project.api;

import com.bing.lan.core.api.ApiManager;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.bing.lan.core.api.HttpParamUtil.checkNotEmptyAdd;

/**
 * Created by 蓝兵 on 2018/6/13.
 */

public class UserControllerTest {

    @Test
    public void testLogin() {

        Map<String, String> params = new HashMap<String, String>();
        checkNotEmptyAdd(params, "13333323235673", "mobile");
        checkNotEmptyAdd(params, "111111111111", "password");

        ApiManager.getInstance()
                .getApiService()
                .login(params)
                //.subscribeOn(Schedulers.io())
                //.subscribe();
                //.subscribe(new Consumer<Object>() {
                //    public void accept(Object o) throws Exception {
                //
                //    }
                //});
                .subscribe(new Observer<Object>() {
                    public void onSubscribe(Disposable disposable) {

                    }

                    public void onNext(Object o) {

                    }

                    public void onError(Throwable throwable) {

                    }

                    public void onComplete() {

                    }
                });
    }
}
