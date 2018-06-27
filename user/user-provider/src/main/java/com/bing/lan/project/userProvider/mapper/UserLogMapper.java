package com.bing.lan.project.userProvider.mapper;

import com.bing.lan.project.userApi.domain.UserLog;
import java.util.List;

public interface UserLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserLog record);

    UserLog selectByPrimaryKey(Long id);

    List<UserLog> selectAll();

    int updateByPrimaryKey(UserLog record);
}