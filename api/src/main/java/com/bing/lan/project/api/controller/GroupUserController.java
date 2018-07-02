package com.bing.lan.project.api.controller;

import com.bing.lan.core.api.LogUtil;
import com.bing.lan.holder.UserHolder;
import com.bing.lan.project.api.BaseController;
import com.bing.lan.project.api.interceptor.annotation.RequiredLogin;
import com.bing.lan.project.api.version.ApiVersion;
import com.bing.lan.project.userApi.domain.Groups;
import com.bing.lan.project.userApi.exception.UserException;
import com.bing.lan.project.userApi.service.DubboGroupUserService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 蓝兵 on 2018/6/13.
 */

@Controller
@RequestMapping("/{version}/user/group")
public class GroupUserController extends BaseController {

    private final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);

    @Autowired
    private DubboGroupUserService dubboGroupUserService;

    @ResponseBody
    @RequestMapping("/create")
    @ApiVersion(1)
    @RequiredLogin
    public Groups createGroup1(String groupName) {
        if (StringUtils.isBlank(groupName)) {
            throw new UserException("请填写组名");
        }
        return dubboGroupUserService.createGroup(UserHolder.getUserId(), groupName);
    }
}
