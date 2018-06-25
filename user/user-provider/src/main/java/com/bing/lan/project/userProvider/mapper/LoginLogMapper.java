package com.bing.lan.project.userProvider.mapper;

import com.bing.lan.project.userApi.domain.LoginLog;

import java.util.List;

public interface LoginLogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(LoginLog record);

    LoginLog selectByPrimaryKey(Long id);

    List<LoginLog> selectAll();

    int updateByPrimaryKey(LoginLog record);
}