package com.bing.lan.project.userProvider;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by 蓝兵 on 2018/6/13.
 */

public class DubboUserProvider {

    private static Logger log = LoggerFactory.getLogger(DubboUserProvider.class);

    public static void main(String[] args) {
        try {
            new ClassPathXmlApplicationContext("classpath:user-provider-context.xml");
        } catch (Exception e) {
            log.error(">>>>> DubboUserProvider context start error >>>>", e);
        }
        synchronized (DubboUserProvider.class) {
            while (true) {
                try {
                    DubboUserProvider.class.wait();
                } catch (InterruptedException e) {
                    log.error(">>>>> synchronized error >>>>>", e);
                }
            }
        }
    }
}
