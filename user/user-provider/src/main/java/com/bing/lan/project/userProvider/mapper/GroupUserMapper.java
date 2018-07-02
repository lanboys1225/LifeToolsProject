package com.bing.lan.project.userProvider.mapper;

import com.bing.lan.project.userApi.domain.GroupUser;
import java.util.List;

public interface GroupUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GroupUser record);

    GroupUser selectByPrimaryKey(Long id);

    List<GroupUser> selectAll();

    int updateByPrimaryKey(GroupUser record);
}