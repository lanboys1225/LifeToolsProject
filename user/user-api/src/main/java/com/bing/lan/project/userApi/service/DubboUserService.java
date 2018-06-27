package com.bing.lan.project.userApi.service;

import com.bing.lan.domain.CommRequestParams;
import com.bing.lan.project.userApi.domain.ResetPasswordResult;
import com.bing.lan.project.userApi.domain.User;

/**
 * Created by 蓝兵 on 2018/6/13.
 */
public interface DubboUserService {

    User doLogin(CommRequestParams commRequestParams, String phone, String password);

    User doRegister(CommRequestParams commRequestParams, String phone,
            String password, String nickName, String userName);

    ResetPasswordResult resetLoginPassword(CommRequestParams commRequestParams, String phone,
            String password, String newPassword);
}
