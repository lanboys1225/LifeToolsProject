package com.bing.lan.project.userProvider;

import com.bing.lan.core.api.LogUtil;
import com.bing.lan.project.userApi.DubboUserService;
import com.bing.lan.project.userApi.domain.UserBean;

import org.springframework.stereotype.Service;

/**
 * Created by 蓝兵 on 2018/6/13.
 */

@Service
public class DubboUserServiceImpl implements DubboUserService {

    private final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);

    public UserBean doLogin(String mobile, String password) {
        //System.out.println("DubboUserServiceImpl doLogin() >>>>>>" + mobile + " 登录成功");
        log.i("doLogin() >>>>>>" + mobile + " 登录成功");

        return new UserBean("1", mobile, password);
    }
}
