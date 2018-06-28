package com.bing.lan.project.api.service;

import com.bing.lan.domain.CommRequestParams;
import com.bing.lan.domain.QueryDomain;
import com.bing.lan.project.userApi.domain.ResetPasswordResult;
import com.bing.lan.project.userApi.domain.User;
import com.bing.lan.project.userApi.domain.UserLog;

/**
 * Created by 蓝兵 on 2018/6/13.
 */
public interface UserService {

    User doLogin(CommRequestParams commRequestParams, String phone, String password);

    User doRegister(CommRequestParams commRequestParams, String phone, String password);

    ResetPasswordResult resetLoginPassword(CommRequestParams commRequestParams, String phone,
            String password, String newPassword);

    QueryDomain<UserLog> userLog(String userId, QueryDomain<UserLog> queryDomain);
}
