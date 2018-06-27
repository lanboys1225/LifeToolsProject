package com.bing.lan.project.api.service.impl;

import com.bing.lan.project.api.service.UserService;
import com.bing.lan.project.userApi.service.DubboUserService;
import com.bing.lan.project.userApi.domain.User;

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
    public User doLogin(String phone, String password) {
        return dubboUserService.doLogin(phone, password, "v1.0.0", "bakjoiggalddfdf",
                "pc", "", "192.168.8.240");
    }

    @Override
    public User doRegister(String phone, String password) {
        return dubboUserService.doRegister(phone, password, "", "", "v1.0.0", "bakjoiggalddfdf",
                "pc", "", "192.168.8.240");
    }
}
