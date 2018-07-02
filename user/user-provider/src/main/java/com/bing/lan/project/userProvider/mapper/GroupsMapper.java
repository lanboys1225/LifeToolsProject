package com.bing.lan.project.userProvider.mapper;

import com.bing.lan.project.userApi.domain.Groups;
import java.util.List;

public interface GroupsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Groups record);

    Groups selectByPrimaryKey(Long id);

    List<Groups> selectAll();

    int updateByPrimaryKey(Groups record);
}