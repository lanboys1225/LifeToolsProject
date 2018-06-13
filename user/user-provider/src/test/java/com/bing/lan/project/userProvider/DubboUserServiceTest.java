package com.bing.lan.project.userProvider;

import com.bing.lan.project.userApi.DubboUserService;

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
@ContextConfiguration(value = "classpath:user-provider-context.xml")
public class DubboUserServiceTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testDubboUserServiceDoLogin() {
        DubboUserService dubboUserService = context.getBean(DubboUserService.class);
        dubboUserService.doLogin("110", "120");
    }
}
