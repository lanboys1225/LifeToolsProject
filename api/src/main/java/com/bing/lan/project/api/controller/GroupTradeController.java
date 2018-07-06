package com.bing.lan.project.api.controller;

import com.bing.lan.domain.QueryDomain;
import com.bing.lan.holder.UserHolder;
import com.bing.lan.project.api.BaseController;
import com.bing.lan.project.api.interceptor.annotation.RequiredLogin;
import com.bing.lan.project.api.version.ApiVersion;
import com.bing.lan.project.userApi.domain.ApiCommResult;
import com.bing.lan.project.userApi.domain.GroupTrade;
import com.bing.lan.project.userApi.domain.Groups;
import com.bing.lan.project.userApi.exception.UserException;
import com.bing.lan.project.userApi.service.DubboGroupTradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 蓝兵 on 2018/7/6.
 */

@Controller
@RequestMapping("/{version}/user/group/trade")
public class GroupTradeController extends BaseController {

    @Autowired
    private DubboGroupTradeService dubboGroupTradeService;

    @ResponseBody
    @RequestMapping("/createTrade")
    @ApiVersion(1)
    @RequiredLogin
    public GroupTrade createTrade(String tradeType, long groupId, long tradeAmount) {
        if (tradeAmount == 0) {
            throw new UserException("请输入赞赏金额");
        }
        return dubboGroupTradeService.createTrade(tradeType, UserHolder.getUserId(), groupId, tradeAmount);
    }

    @ResponseBody
    @RequestMapping("/auditTrade")
    @ApiVersion(1)
    @RequiredLogin
    public ApiCommResult auditTrade(long tradeId, String auditStatus, String auditFailReason) {
        return dubboGroupTradeService.auditTrade(tradeId, UserHolder.getUserId(), auditStatus,
                auditFailReason);
    }

    @ResponseBody
    @RequestMapping("/findAllTradeOfGroupList")
    @ApiVersion(1)
    @RequiredLogin
    public QueryDomain<GroupTrade> findAllTradeOfGroupList(long groupId,
            @RequestParam(required = false, defaultValue = "20") int pageSize,
            @RequestParam(required = false, defaultValue = "1") int currentPage) {

        return dubboGroupTradeService.findAllTradeOfGroupList(groupId, new QueryDomain<>(pageSize, currentPage));
    }
}
