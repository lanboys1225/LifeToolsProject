package com.bing.lan.project.userProvider.service.impl;

import com.bing.lan.project.userApi.domain.GroupUser;
import com.bing.lan.project.userApi.domain.Groups;
import com.bing.lan.project.userApi.service.DubboGroupUserService;
import com.bing.lan.project.userProvider.mapper.GroupUserMapper;
import com.bing.lan.project.userProvider.mapper.GroupsMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 蓝兵 on 2018/7/2.
 */
@Service
public class DubboGroupUserServiceImpl implements DubboGroupUserService {

    @Autowired
    private GroupsMapper groupMapper;

    @Autowired
    private GroupUserMapper groupUserMapper;

    @Override
    @Transactional
    public Groups createGroup(String ownerId, String groupName) {
        Groups group = Groups.builder()
                .groupName(groupName)
                .groupOwnerId(Long.valueOf(ownerId))
                .groupNumber(System.currentTimeMillis() + "")
                .build();
        groupMapper.insert(group);
        GroupUser groupUser = GroupUser.builder()
                .groupId(group.getId())
                .userId(Long.valueOf(ownerId))
                .groupRole("creator")
                .build();
        groupUserMapper.insert(groupUser);
        return group;
    }
}
