package com.bing.lan.project.userProvider;

import com.bing.lan.domain.CommRequestParams;
import com.bing.lan.project.userApi.domain.Groups;
import com.bing.lan.project.userApi.service.DubboGroupUserService;
import com.bing.lan.redis.RedisClient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by 蓝兵 on 2018/6/13.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:user-provider-context-test.xml")
public class DubboGroupUserServiceTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private DubboGroupUserService dubboGroupUserService;

    private CommRequestParams commRequestParams;

    {
        commRequestParams = CommRequestParams.builder()
                .platform("android")
                .ip("127.9.0.121")
                .version("v1.1.2")
                .channel("tengxun")
                .build();
    }

    /**
     * 测试 创建组
     */
    @Test
    public void testCreateGroup() {
        Groups group = dubboGroupUserService.createGroup("1", "groupName");
        System.out.println("testCreateGroup(): "+group);

    }


}
