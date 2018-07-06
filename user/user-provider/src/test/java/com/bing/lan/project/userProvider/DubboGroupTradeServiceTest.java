package com.bing.lan.project.userProvider;

import com.bing.lan.project.userApi.service.DubboGroupTradeService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by 蓝兵 on 2018/7/5.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:user-provider-context-test.xml")
public class DubboGroupTradeServiceTest {

    @Autowired
    private DubboGroupTradeService dubboGroupTradeService;

    /**
     * 测试 创建赞助流水
     */
    @Test
    public void testCreateTrade() {
        dubboGroupTradeService.createTrade("sponsor", 1,
                30, 1000);
    }

    /**
     * 测试 审核 流水
     */
    @Test
    public void testAuditTrade() {
        dubboGroupTradeService.auditTrade(2,
                1, "success", null);
    }
}
