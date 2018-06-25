package com.bing.lan.project.userProvider;

import com.bing.lan.project.userApi.constants.RedisConstant;
import com.bing.lan.project.userApi.service.DubboUserService;
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
public class DubboUserServiceTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private DubboUserService dubboUserService;

    /**
     * 测试登录
     */
    @Test
    public void testDubboUserServiceDoLogin() {
        dubboUserService.doLogin("13556000000", "12345", "v1.0.0", "bakjoiggalddfdf",
                "pc", "", "192.168.8.240");
    }

    /**
     * 清除redis 密码错误次数 缓存
     */
    @Test
    public void testCleanRedisPwdErrorNum() {
        String user_id = "1";
        String key = RedisConstant.REDIS_PWD_ERROR_NUM_KEY + user_id;
        redisClient.putString(key, "0");
    }
}
