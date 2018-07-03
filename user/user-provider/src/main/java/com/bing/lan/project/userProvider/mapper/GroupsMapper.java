package com.bing.lan.project.userProvider.mapper;

import com.bing.lan.project.userApi.domain.Groups;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Groups record);

    Groups selectByPrimaryKey(Long id);

    List<Groups> selectAll();

    List<Groups> selectAllByPagination(
            @Param("offset") int offset,
            @Param("pageSize") int pageSize
    );

    int countAll();

    int updateByPrimaryKey(Groups record);
}