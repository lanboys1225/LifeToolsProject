package com.bing.lan.project.userProvider.mapper;

import com.bing.lan.project.userApi.domain.GroupTradeAccount;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupTradeAccountMapper {

    int deleteByPrimaryKey(Long id);

    int insert(GroupTradeAccount record);

    GroupTradeAccount selectByPrimaryKey(Long id);

    List<GroupTradeAccount> selectAll();

    int updateByPrimaryKey(GroupTradeAccount record);

    int updateAvailableAmountByGroupId(@Param("groupId") Long groupId,
            @Param("deltaAmount") long deltaAmount);
}