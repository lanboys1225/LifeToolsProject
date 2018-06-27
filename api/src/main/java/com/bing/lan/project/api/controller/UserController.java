package com.bing.lan.project.api.controller;

import com.bing.lan.core.api.LogUtil;
import com.bing.lan.project.api.BaseController;
import com.bing.lan.project.api.service.UserService;
import com.bing.lan.project.api.interceptor.annotation.RequiredLogin;
import com.bing.lan.project.api.version.ApiVersion;
import com.bing.lan.project.userApi.domain.User;
import com.bing.lan.project.userApi.exception.UserException;

import org.apache.commons.lang.StringUtils;
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
    public User login1(String phone, String password) {
        log.i("login1(): 版本1登录");
        User user = login(phone, password);
        user.setMsg("登录成功");
        return user;
    }

    @ResponseBody
    @RequestMapping("/login")
    @ApiVersion(2)
    @RequiredLogin
    public User login2(String phone, String password) {
        log.i("login2(): 版本2登录");
        User user = login(phone, password);
        user.setMsg("登录成功");
        return user;
    }

    @ResponseBody
    @RequestMapping("/login")
    @ApiVersion(5)
    public User login5(String phone, String password) {
        log.i("login5(): 版本5登录");
        User user = login(phone, password);
        user.setMsg("登录成功");
        return user;
    }

    private User login(String phone, String password) {
        if (StringUtils.isBlank(phone)) {
            throw new UserException("请填写手机号码");
        }
        if (StringUtils.isBlank(password)) {
            throw new UserException("请填写登录密码");
        }
        return userService.doLogin(phone, password);
    }

    @ResponseBody
    @RequestMapping("/register")
    @ApiVersion(1)
    public User register(String phone, String password) {
        if (StringUtils.isBlank(phone)) {
            throw new UserException("请填写手机号码");
        }
        User user = userService.doRegister(phone, password);
        user.setMsg("注册成功");
        return user;
    }
}
