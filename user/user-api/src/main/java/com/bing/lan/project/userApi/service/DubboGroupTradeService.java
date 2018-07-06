package com.bing.lan.project.userApi.service;

import com.bing.lan.domain.QueryDomain;
import com.bing.lan.project.userApi.domain.ApiCommResult;
import com.bing.lan.project.userApi.domain.GroupTrade;

/**
 * Created by 蓝兵 on 2018/7/5.
 */

public interface DubboGroupTradeService {

    GroupTrade createTrade(String tradeType, long tradeUserId, long groupId, long tradeAmount);

    ApiCommResult auditTrade(long tradeId, long auditUserId, String auditStatus, String auditFailReason);

    QueryDomain<GroupTrade> findAllTradeOfGroupList(long groupId, QueryDomain<GroupTrade> queryDomain);
}
