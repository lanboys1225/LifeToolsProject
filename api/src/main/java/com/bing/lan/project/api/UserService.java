package com.bing.lan.project.api;

import com.bing.lan.project.userApi.domain.User;

/**
 * Created by 蓝兵 on 2018/6/13.
 */
public interface UserService {

    User doLogin(String mobile, String password, String nickName);
}
