package com.bing.lan.project.api.controller;

import com.bing.lan.project.api.BaseController;
import com.bing.lan.project.api.UserService;
import com.bing.lan.project.api.version.ApiVersion;
import com.bing.lan.project.userApi.domain.UserBean;

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
    public UserBean login1(String mobile, String password, String nickName) {
        return userService.doLogin(mobile + " 版本 1", password, nickName);
    }

    @ResponseBody
    @RequestMapping("/login")
    @ApiVersion(2)
    public UserBean login2(String mobile, String password, String nickName) {
        return userService.doLogin(mobile + " 版本 2", password, nickName);
    }

    @ResponseBody
    @RequestMapping("/login")
    @ApiVersion(5)
    public UserBean login5(String mobile, String password, String nickName) {
        return userService.doLogin(mobile + " 版本 5", password, nickName);
    }
}
