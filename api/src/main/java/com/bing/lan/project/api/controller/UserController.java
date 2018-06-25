package com.bing.lan.project.api.controller;

import com.bing.lan.core.api.LogUtil;
import com.bing.lan.project.api.BaseController;
import com.bing.lan.project.api.service.UserService;
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

    private final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/login")
    @ApiVersion(1)
    public User login1(String mobile, String password) {
        log.i("login1(): 版本1登录");
        return login(mobile, password);
    }

    @ResponseBody
    @RequestMapping("/login")
    @ApiVersion(2)
    @RequiredLogin
    public User login2(String mobile, String password) {
        log.i("login2(): 版本2登录");
        return login(mobile, password);
    }

    @ResponseBody
    @RequestMapping("/login")
    @ApiVersion(5)
    public User login5(String mobile, String password) {
        log.i("login5(): 版本5登录");
        return login(mobile, password);
    }

    private User login(String mobile, String password) {
        return userService.doLogin(mobile, password);
    }
}
