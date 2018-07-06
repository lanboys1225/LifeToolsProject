package com.bing.lan.project.userProvider.service.impl;

import com.bing.lan.domain.QueryDomain;
import com.bing.lan.project.userApi.domain.ApiCommResult;
import com.bing.lan.project.userApi.domain.GroupApproval;
import com.bing.lan.project.userApi.domain.GroupTradeAccount;
import com.bing.lan.project.userApi.domain.GroupUser;
import com.bing.lan.project.userApi.domain.Groups;
import com.bing.lan.project.userApi.exception.UserException;
import com.bing.lan.project.userApi.service.DubboGroupUserService;
import com.bing.lan.project.userProvider.mapper.GroupApprovalMapper;
import com.bing.lan.project.userProvider.mapper.GroupTradeAccountMapper;
import com.bing.lan.project.userProvider.mapper.GroupUserMapper;
import com.bing.lan.project.userProvider.mapper.GroupsMapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by 蓝兵 on 2018/7/2.
 */
@Service
public class DubboGroupUserServiceImpl implements DubboGroupUserService {

    @Autowired
    private GroupTradeAccountMapper groupTradeAccountMapper;

    @Autowired
    private GroupsMapper groupsMapper;

    @Autowired
    private GroupUserMapper groupUserMapper;

    @Autowired
    private GroupApprovalMapper groupApprovalMapper;

    /**
     * 创建 群
     */
    @Override
    @Transactional
    public Groups createGroup(long ownerId, String groupName) {
        Groups group = Groups.builder()
                .groupName(groupName)
                .groupOwnerId(ownerId)
                .groupNumber(System.currentTimeMillis() + "")
                .build();
        groupsMapper.insert(group);
        // 加入自己创建的群
        joinGroup(group.getId(), ownerId, "creator");

        GroupTradeAccount groupTradeAccount = GroupTradeAccount.builder()
                .availableAmount(0L)
                .groupId(group.getId())
                .build();
        groupTradeAccountMapper.insert(groupTradeAccount);
        return group;
    }

    /**
     * 加入群
     */
    private GroupUser joinGroup(long groupId, long userId, String groupRole) {

        if (StringUtils.isBlank(groupRole)) {
            groupRole = "member";
        }
        GroupUser groupUser = GroupUser.builder()
                .groupId(groupId)
                .userId(userId)
                .groupRole(groupRole)
                .build();
        int insert = groupUserMapper.insert(groupUser);
        if (insert == 0) {
            throw new UserException("早已加入群");
        }
        return groupUser;
    }

    /**
     * 申请 加入 群
     */
    @Override
    public GroupApproval applyJoinGroup(long groupId, long userId) {

        GroupApproval groupApproval = GroupApproval.builder()
                .groupId(groupId)
                .groupJoinUserId(userId)
                .build();
        int insert = groupApprovalMapper.insert(groupApproval);
        if (insert == 0) {
            throw new UserException("申请已提交, 请耐心等待");
        }
        return groupApproval;
    }

    @Override
    @Transactional
    public ApiCommResult approvalJoinGroup(long approvalUserId, long groupId, long groupJoinUserId,
            String approvalStatus, String failCause) {

        Groups groups = groupsMapper.selectByPrimaryKey(groupId);
        if (approvalUserId != groups.getGroupOwnerId()) {
            throw new UserException("只有群主才有审核资格");
        }

        GroupApproval groupApproval = groupApprovalMapper.selectByGroupIdAndGroupJoinUserId(
                groupId, groupJoinUserId);

        if ("success".equals(groupApproval.getApprovalStatus())) {
            throw new UserException("已审核通过，无需再次审核");
        }
        groupApproval.setApprovalStatus(approvalStatus);
        groupApproval.setApprovalFailCause(failCause);
        groupApproval.setApprovalTime(new Date());
        groupApproval.setApprovalUserId(approvalUserId);
        groupApprovalMapper.updateByPrimaryKey(groupApproval);

        if ("success".equals(approvalStatus)) {
            joinGroup(groupId, groupJoinUserId, "");
        }

        return ApiCommResult.builder().isSuccess(true).build();
    }

    /**
     * 需要审核的加入群申请 列表
     */
    @Override
    public QueryDomain<GroupApproval> groupApprovalList(long groupId) {
        List<GroupApproval> groupApprovals = groupApprovalMapper.selectAllByGroupId(groupId);

        QueryDomain<GroupApproval> queryDomain = new QueryDomain<>();
        queryDomain.setList(groupApprovals);
        queryDomain.setTotalSize(groupApprovalMapper.countAll());

        return queryDomain;
    }

    /**
     * 所有 群列表
     */
    @Override
    public QueryDomain<Groups> findAllGroupList(QueryDomain<Groups> queryDomain) {
        List<Groups> groups = groupsMapper.selectAllByPagination(
                queryDomain.getOffset(), queryDomain.getPageSize());
        queryDomain.setList(groups);
        queryDomain.setTotalSize(groupsMapper.countAll());
        return queryDomain;
    }

    /**
     * 用户加入的群列表
     */
    @Override
    public QueryDomain<GroupUser> findGroupOfUserJoinList(long userId, QueryDomain<GroupUser> queryDomain) {
        List<GroupUser> groupUsers = groupUserMapper.selectAllByUserId(userId,
                queryDomain.getOffset(), queryDomain.getPageSize());
        queryDomain.setList(groupUsers);
        queryDomain.setTotalSize(groupUserMapper.countByUserId(userId));
        return queryDomain;
    }

    /**
     * 群成员列表
     */
    @Override
    public QueryDomain<GroupUser> findMemberOfGroupList(long groupId, QueryDomain<GroupUser> queryDomain) {
        List<GroupUser> groupUsers = groupUserMapper.selectAllByGroupId(groupId,
                queryDomain.getOffset(), queryDomain.getPageSize());
        queryDomain.setList(groupUsers);
        queryDomain.setTotalSize(groupUserMapper.countByGroupId(groupId));
        return queryDomain;
    }
}
