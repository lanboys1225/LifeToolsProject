package com.bing.lan.project.userApi;

import com.bing.lan.project.userApi.domain.User;

/**
 * Created by 蓝兵 on 2018/6/13.
 */
public interface DubboUserService {

    User doLogin(String phone, String password);
}
