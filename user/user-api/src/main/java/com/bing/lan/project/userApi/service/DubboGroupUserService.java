package com.bing.lan.project.userApi.service;

import com.bing.lan.project.userApi.domain.Groups;

/**
 * Created by 蓝兵 on 2018/7/2.
 */

public interface DubboGroupUserService {

    Groups createGroup(String ownerId, String groupName);
}
