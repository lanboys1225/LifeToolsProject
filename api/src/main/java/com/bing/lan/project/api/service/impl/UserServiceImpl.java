package com.bing.lan.project.api.service.impl;

import com.bing.lan.domain.CommRequestParams;
import com.bing.lan.project.api.service.UserService;
import com.bing.lan.project.userApi.domain.ResetPasswordResult;
import com.bing.lan.project.userApi.domain.User;
import com.bing.lan.project.userApi.service.DubboUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 蓝兵 on 2018/6/13.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private DubboUserService dubboUserService;

    @Override
    public User doLogin(CommRequestParams commRequestParams, String phone, String password) {
        return dubboUserService.doLogin(commRequestParams, phone, password);
    }

    @Override
    public User doRegister(CommRequestParams commRequestParams, String phone, String password) {
        return dubboUserService.doRegister(commRequestParams, phone, password, "nickname", "username");
    }

    @Override
    public ResetPasswordResult resetLoginPassword(CommRequestParams commRequestParams, String phone,
            String password, String newPassword) {
        return dubboUserService.resetLoginPassword(commRequestParams, phone, password, newPassword);
    }
}
