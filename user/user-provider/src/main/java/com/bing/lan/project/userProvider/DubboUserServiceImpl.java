package com.bing.lan.project.userProvider;

import com.bing.lan.core.api.LogUtil;
import com.bing.lan.exception.BaseException;
import com.bing.lan.project.userApi.DubboUserService;
import com.bing.lan.project.userApi.domain.UserBean;
import com.bing.lan.redis.RedisClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 蓝兵 on 2018/6/13.
 */

@Service
public class DubboUserServiceImpl implements DubboUserService {

    private final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);

    @Autowired
    private RedisClient redisClient;

    public UserBean doLogin(String mobile, String password) {
        //System.out.println("DubboUserServiceImpl doLogin() >>>>>>" + mobile + " 登录成功");
        log.i("doLogin() >>>>>>" + mobile + " 登录成功");

        try {
            String id = redisClient.getString("user_id");
            if (id == null) {
                redisClient.putString("user_id", "1");
            } else {
                Integer integer = Integer.valueOf(id);
                redisClient.putString("user_id", String.valueOf(++integer));
            }
        } catch (Exception e) {
            log.e("doLogin():  " + e.getLocalizedMessage());
        }

        long l = Long.valueOf(password) % 2;
        if (l == 0) {
            int i = 12 / 0;
        } else {
            throw new BaseException("用户模块业务异常");
        }

        return new UserBean("1", mobile, password);
    }
}
