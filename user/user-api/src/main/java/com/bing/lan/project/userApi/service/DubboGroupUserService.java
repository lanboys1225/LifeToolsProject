package com.bing.lan.project.userApi.service;

import com.bing.lan.domain.QueryDomain;
import com.bing.lan.project.userApi.domain.ApiCommResult;
import com.bing.lan.project.userApi.domain.GroupApproval;
import com.bing.lan.project.userApi.domain.GroupUser;
import com.bing.lan.project.userApi.domain.Groups;

/**
 * Created by 蓝兵 on 2018/7/2.
 */

public interface DubboGroupUserService {

    Groups createGroup(long ownerId, String groupName);

    GroupApproval applyJoinGroup(long groupId, long userId);

    ApiCommResult approvalJoinGroup(long approvalUserId, long groupId, long groupJoinUserId,
            String approvalStatus, String failCause);

    QueryDomain<GroupApproval> groupApprovalList(long userId);

    QueryDomain<Groups> findAllGroupList(QueryDomain<Groups> queryDomain);

    QueryDomain<GroupUser> findGroupOfUserJoinList(long userId, QueryDomain<GroupUser> queryDomain);

    QueryDomain<GroupUser> findMemberOfGroupList(long groupId, QueryDomain<GroupUser> queryDomain);
}
