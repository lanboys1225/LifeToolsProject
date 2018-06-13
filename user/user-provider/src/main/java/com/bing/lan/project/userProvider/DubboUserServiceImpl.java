package com.bing.lan.project.userProvider;

import com.bing.lan.project.userApi.DubboUserService;

import org.springframework.stereotype.Service;

/**
 * Created by 蓝兵 on 2018/6/13.
 */

@Service
public class DubboUserServiceImpl implements DubboUserService {

    public void doLogin(String mobile, String password) {
        System.out.println("doLogin() >>>>>>" + mobile + " 登录成功");
    }
}
