package com.bing.lan.project.userProvider;

import com.bing.lan.core.api.LogUtil;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by 蓝兵 on 2018/6/13.
 */

public class DubboUserProvider {

    private static final LogUtil log = LogUtil.getLogUtil(DubboUserProvider.class, LogUtil.LOG_VERBOSE);

    public static void main(String[] args) {
        try {
            new ClassPathXmlApplicationContext("classpath:user-provider-context.xml");
            log.i("服务启动成功.....");
        } catch (Exception e) {
            log.e("DubboUserProvider context start error >>>>", e);
        }
        synchronized (DubboUserProvider.class) {
            while (true) {
                try {
                    DubboUserProvider.class.wait();
                } catch (InterruptedException e) {
                    log.e("synchronized error >>>>>", e);
                }
            }
        }
    }
}
