package com.bing.lan.project.userProvider.mapper;

import com.bing.lan.project.userApi.domain.GroupTrade;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupTradeMapper {

    int deleteByPrimaryKey(Long id);

    int insert(GroupTrade record);

    GroupTrade selectByPrimaryKey(Long id);

    List<GroupTrade> selectAllByGroupId(
            @Param("groupId") long groupId,
            @Param("offset") int offset,
            @Param("pageSize") int pageSize
    );

    List<GroupTrade> selectAll();

    int countByGroupId(@Param("groupId") long groupId);

    int countByTradeUserId(@Param("tradeUserId") long tradeUserId);

    int updateByPrimaryKey(GroupTrade record);
}