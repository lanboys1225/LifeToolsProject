package com.bing.lan.project.api;

import com.bing.lan.project.userApi.DubboUserService;
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

    public User doLogin(String mobile, String password, String nickName) {
        System.out.println("UserServiceImpl doLogin() >>>>>>" + mobile + " 请求登录");
        return dubboUserService.doLogin(mobile, password);
    }
}
