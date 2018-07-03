package com.bing.lan.project.userProvider.mapper;

import com.bing.lan.project.userApi.domain.UserLog;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserLogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserLog record);

    UserLog selectByPrimaryKey(Long id);

    int countByUserId(@Param("userId") long userId);

    int countByPhone(@Param("phone") long phone);

    List<UserLog> selectAllByUserId(
            @Param("userId") long userId,
            @Param("offset") int offset,
            @Param("pageSize") int pageSize
    );

    List<UserLog> selectAllByPhone(
            @Param("phone") long phone,
            @Param("offset") int offset,
            @Param("pageSize") int pageSize);

    List<UserLog> selectAll();

    int updateByPrimaryKey(UserLog record);
}