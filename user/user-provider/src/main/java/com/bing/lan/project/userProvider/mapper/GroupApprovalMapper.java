package com.bing.lan.project.userProvider.mapper;

import com.bing.lan.project.userApi.domain.GroupApproval;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupApprovalMapper {

    int deleteByPrimaryKey(Long id);

    int insert(GroupApproval record);

    GroupApproval selectByPrimaryKey(Long id);

    GroupApproval selectByGroupIdAndGroupJoinUserId(
            @Param("groupId") long groupId,
            @Param("groupJoinUserId") long groupJoinUserId);

    List<GroupApproval> selectAllByGroupId(@Param("groupId") long groupId);

    List<GroupApproval> selectAll();

    int countAll();

    int updateByPrimaryKey(GroupApproval record);
}