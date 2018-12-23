package com.bing.lan.project.api.controller;

import com.bing.lan.project.api.BaseController;
import com.bing.lan.project.api.UserService;
import com.bing.lan.project.api.interceptor.annotation.RequiredLogin;
import com.bing.lan.project.api.version.ApiVersion;
import com.bing.lan.project.userApi.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 蓝兵 on 2018/6/13.
 */

@Controller
@RequestMapping("/{version}/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/login")
    @ApiVersion(1)
    public User login1(String mobile, String password, String nickName) {
        log.i("login1(): 版本1登录");
        return login(mobile, password, nickName);
    }

    @ResponseBody
    @RequestMapping("/login")
    @ApiVersion(2)
    @RequiredLogin
    public User login2(String mobile, String password, String nickName) {
        log.i("login2(): 版本2登录");
        return login(mobile, password, nickName);
    }

    @ResponseBody
    @RequestMapping("/login")
    @ApiVersion(5)
    public User login5(String mobile, String password, String nickName) {
        log.i("login5(): 版本5登录");
        return login(mobile, password, nickName);
    }

    private User login(String mobile, String password, String nickName) {
        return userService.doLogin(mobile, password, nickName);
    }
}
