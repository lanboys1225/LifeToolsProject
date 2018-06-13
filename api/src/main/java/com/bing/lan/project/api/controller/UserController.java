package com.bing.lan.project.api.controller;

import com.bing.lan.project.api.BaseController;
import com.bing.lan.project.api.UserService;
import com.bing.lan.project.userApi.domain.UserBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 蓝兵 on 2018/6/13.
 */

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/login")
    public UserBean login(String mobile, String password, String nickName) {
        return userService.doLogin(mobile, password, nickName);
    }
}
