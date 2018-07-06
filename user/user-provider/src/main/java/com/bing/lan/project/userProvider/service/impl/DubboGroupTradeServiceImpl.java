package com.bing.lan.project.userProvider.service.impl;

import com.bing.lan.domain.QueryDomain;
import com.bing.lan.project.userApi.domain.ApiCommResult;
import com.bing.lan.project.userApi.domain.GroupTrade;
import com.bing.lan.project.userApi.domain.GroupUser;
import com.bing.lan.project.userApi.exception.UserException;
import com.bing.lan.project.userApi.service.DubboGroupTradeService;
import com.bing.lan.project.userProvider.mapper.GroupTradeAccountMapper;
import com.bing.lan.project.userProvider.mapper.GroupTradeMapper;
import com.bing.lan.project.userProvider.mapper.GroupUserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 蓝兵 on 2018/7/5.
 */
@Service
public class DubboGroupTradeServiceImpl implements DubboGroupTradeService {

    @Autowired
    GroupTradeMapper groupTradeMapper;

    @Autowired
    GroupTradeAccountMapper groupTradeAccountMapper;

    @Autowired
    GroupUserMapper groupUserMapper;

    @Override
    public GroupTrade createTrade(String tradeType, long tradeUserId, long groupId, long tradeAmount) {
        GroupTrade groupTradeExpense = GroupTrade.builder()
                .tradeUserId(tradeUserId)
                .groupId(groupId)
                .tradeAmount(tradeAmount)
                .tradeType(tradeType)
                .build();
        groupTradeMapper.insert(groupTradeExpense);
        return groupTradeExpense;
    }

    @Override
    public ApiCommResult auditTrade(long tradeId, long auditUserId, String auditStatus, String auditFailReason) {
        GroupTrade groupTrade = groupTradeMapper.selectByPrimaryKey(tradeId);

        if ("success".equals(groupTrade.getAuditStatus())) {
            throw new UserException("报销单已审核通过，无需再次审核");
        }

        // 校验 审核者权限
        GroupUser groupUser = groupUserMapper.selectByGroupIdAndUserId(groupTrade.getGroupId(),
                auditUserId);
        if (groupUser == null || !"cfo".equals(groupUser.getGroupRole())) {
            throw new UserException("您无权限审核该报销单");
        }

        groupTrade.setAuditUserId(auditUserId);
        groupTrade.setAuditStatus(auditStatus);
        groupTrade.setAuditFailReason(auditFailReason);

        groupTradeMapper.updateByPrimaryKey(groupTrade);

        if ("success".equals(auditStatus)) {
            groupTradeAccountMapper.updateAvailableAmountByGroupId(groupTrade.getGroupId(),
                    groupTrade.getTradeAmount());
        }

        return ApiCommResult.builder().isSuccess(true).build();
    }

    @Override
    public QueryDomain<GroupTrade> findAllTradeOfGroupList(long groupId, QueryDomain<GroupTrade> queryDomain) {

        List<GroupTrade> groupTrades = groupTradeMapper.selectAllByGroupId(groupId,
                queryDomain.getOffset(), queryDomain.getPageSize());

        queryDomain.setList(groupTrades);
        queryDomain.setTotalSize(groupTradeMapper.countByGroupId(groupId));
        return queryDomain;
    }
}
