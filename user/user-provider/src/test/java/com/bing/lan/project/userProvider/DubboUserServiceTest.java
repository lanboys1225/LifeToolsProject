package com.bing.lan.project.userProvider;

import com.bing.lan.domain.CommRequestParams;
import com.bing.lan.domain.QueryDomain;
import com.bing.lan.project.userApi.constants.RedisConstant;
import com.bing.lan.project.userApi.domain.UserLog;
import com.bing.lan.project.userApi.service.DubboUserService;
import com.bing.lan.redis.RedisClient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

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
     * 清除redis 密码错误次数 缓存
     */
    @Test
    public void testCleanRedisPwdErrorNum() {
        String user_id = "1";
        String key = RedisConstant.REDIS_PWD_ERROR_NUM_KEY + user_id;
        redisClient.putString(key, "0");
    }

    /**
     * 测试登录
     */
    @Test
    public void testDubboUserServiceDoLogin() {
        dubboUserService.doLogin(commRequestParams, "13556000000",
                "12345");
    }

    /**
     * 测试注册
     */
    @Test
    public void testDubboUserServiceDoRegister() {
        dubboUserService.doRegister(commRequestParams, "13556000000",
                "", "", "");
    }

    /**
     * 测试重置支付密码
     */
    @Test
    public void testDubboUserServiceResetLoginPassword() {
        dubboUserService.resetLoginPassword(commRequestParams, "13556000000",
                "12345", "123456");
    }

    /**
     * 测试查询用户日志
     */
    @Test
    public void testDubboUserServiceUserLog() {
        QueryDomain queryDomain = new QueryDomain();
        queryDomain.setCurrentPage(1);
        queryDomain.setPageSize(5);

        QueryDomain userLogs1 = dubboUserService.userLog("10", queryDomain);
        List<UserLog> userLogs = userLogs1.getList();

        System.out.println("testDubboUserServiceUserLog()--------------------------------");
        for (UserLog userLog : userLogs) {
            System.out.println("testDubboUserServiceUserLog(): " + userLog.getId());
        }
        System.out.println("testDubboUserServiceUserLog()--------------------------------");
    }
}
