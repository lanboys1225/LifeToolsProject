package com.bing.lan.project.userProvider.mapper;

import com.bing.lan.project.userApi.domain.GroupUser;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(GroupUser record);

    GroupUser selectByPrimaryKey(Long id);

    List<GroupUser> selectAll();

    List<GroupUser> selectAllByUserId(
            @Param("userId") long userId,
            @Param("offset") int offset,
            @Param("pageSize") int pageSize
    );

    List<GroupUser> selectAllByGroupId(
            @Param("groupId") long groupId,
            @Param("offset") int offset,
            @Param("pageSize") int pageSize
    );

    int countAllByUserId(@Param("userId") long userId);

    int countAllByGroupId(@Param("groupId") long groupId);

    int updateByPrimaryKey(GroupUser record);
}