package com.bing.lan.project.userProvider.mapper;

import com.bing.lan.project.userApi.domain.User;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    User selectByPrimaryKey(Long id);

    User selectByPhone(@Param("phone") String phone);

    User selectByPhoneAndPassword(@Param("phone") String phone,
            @Param("password") String password);

    List<User> selectAll();

    int updateByPrimaryKey(User record);
}