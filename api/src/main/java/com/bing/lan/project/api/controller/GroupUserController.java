package com.bing.lan.project.api.controller;

import com.bing.lan.core.api.LogUtil;
import com.bing.lan.domain.QueryDomain;
import com.bing.lan.holder.UserHolder;
import com.bing.lan.project.api.BaseController;
import com.bing.lan.project.api.interceptor.annotation.RequiredLogin;
import com.bing.lan.project.api.version.ApiVersion;
import com.bing.lan.project.userApi.domain.ApiCommResult;
import com.bing.lan.project.userApi.domain.GroupApproval;
import com.bing.lan.project.userApi.domain.GroupUser;
import com.bing.lan.project.userApi.domain.Groups;
import com.bing.lan.project.userApi.exception.UserException;
import com.bing.lan.project.userApi.service.DubboGroupUserService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 蓝兵 on 2018/6/13.
 */

@Controller
@RequestMapping("/{version}/user/group")
public class GroupUserController extends BaseController {

    //private final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);

    @Autowired
    private DubboGroupUserService dubboGroupUserService;

    @ResponseBody
    @RequestMapping("/createGroup")
    @ApiVersion(1)
    @RequiredLogin
    public Groups createGroup(String groupName) {
        if (StringUtils.isBlank(groupName)) {
            throw new UserException("请填写组名");
        }
        return dubboGroupUserService.createGroup(UserHolder.getUserId(), groupName);
    }

    @ResponseBody
    @RequestMapping("/groupApprovalList")
    @ApiVersion(1)
    @RequiredLogin
    public QueryDomain<GroupApproval> groupApprovalList(long groupId) {
        return dubboGroupUserService.groupApprovalList(groupId);
    }

    @ResponseBody
    @RequestMapping("/applyJoinGroup")
    @ApiVersion(1)
    @RequiredLogin
    public GroupApproval applyJoinGroup(long groupId) {
        return dubboGroupUserService.applyJoinGroup(groupId, UserHolder.getUserId());
    }

    @ResponseBody
    @RequestMapping("/approvalJoinGroup")
    @ApiVersion(1)
    @RequiredLogin
    public ApiCommResult approvalJoinGroup(long groupId, long groupJoinUserId,
            String approvalStatus, String failCause) {
        return dubboGroupUserService.approvalJoinGroup(UserHolder.getUserId(),
                groupId, groupJoinUserId, approvalStatus, failCause);
    }

    @ResponseBody
    @RequestMapping("/findAllGroupList")
    @ApiVersion(1)
    public QueryDomain<Groups> findAllGroupList(
            @RequestParam(required = false, defaultValue = "20") int pageSize,
            @RequestParam(required = false, defaultValue = "1") int currentPage) {

        return dubboGroupUserService.findAllGroupList(new QueryDomain<>(pageSize, currentPage));
    }

    // 用户加入的群列表
    @ResponseBody
    @RequestMapping("/findGroupOfUserJoinList")
    @ApiVersion(1)
    @RequiredLogin
    public QueryDomain<GroupUser> findGroupOfUserJoinList(
            @RequestParam(required = false, defaultValue = "20") int pageSize,
            @RequestParam(required = false, defaultValue = "1") int currentPage) {

        return dubboGroupUserService.findGroupOfUserJoinList(UserHolder.getUserId(),
                new QueryDomain<>(pageSize, currentPage));
    }

    // 群成员列表
    @ResponseBody
    @RequestMapping("/findMemberOfGroupList")
    @ApiVersion(1)
    @RequiredLogin
    public QueryDomain<GroupUser> findMemberOfGroupList(long groupId,
            @RequestParam(required = false, defaultValue = "20") int pageSize,
            @RequestParam(required = false, defaultValue = "1") int currentPage) {

        return dubboGroupUserService.findMemberOfGroupList(groupId,
                new QueryDomain<>(pageSize, currentPage));
    }
}
