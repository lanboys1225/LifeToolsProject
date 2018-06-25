package com.bing.lan.project.userProvider.service.impl;

import com.bing.lan.core.api.LogUtil;
import com.bing.lan.project.userApi.constants.Constant;
import com.bing.lan.project.userApi.constants.RedisConstant;
import com.bing.lan.project.userApi.domain.User;
import com.bing.lan.project.userApi.exception.UserException;
import com.bing.lan.project.userApi.service.DubboUserService;
import com.bing.lan.project.userProvider.mapper.UserMapper;
import com.bing.lan.redis.RedisClient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * Created by 蓝兵 on 2018/6/13.
 */

@Service
public class DubboUserServiceImpl implements DubboUserService {

    private final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private UserMapper userMapper;

    public User doLogin(String phone, String password) {

        User user = userMapper.selectByPhone(phone);
        if (user == null || "Y".equalsIgnoreCase(user.getIsDelete())) {
            throw new UserException("用户不存在");
        }


        if (StringUtils.isBlank(user.getPassword())) {
            throw new UserException("您还未设置登录密码，请重置密码");
        }

        String key = RedisConstant.REDIS_PWD_ERROR_NUM_KEY + user.getId();

        String errorNum = redisClient.getString(key);
        Integer num = 0;

        if (!StringUtils.isBlank(user.getPassword())) {
            num = Integer.parseInt(errorNum);
            if (num >= Constant.ERROR_PWD_NUM) {
                throw new UserException("您输入的密码错误，今日机会已用完，请找回密码");
            }
        }

        if (!user.getPassword().equals(password)) {

            //计算距离当天结束时间
            long milliSecondsLeftToday = 86400000 -
                    DateUtils.getFragmentInMilliseconds(Calendar.getInstance(), Calendar.DATE);

            redisClient.putString(key, (num + 1) + "", milliSecondsLeftToday / 1000);

            throw new UserException("您输入的密码错误，今日还有" + (Constant.ERROR_PWD_NUM - num - 1) + "次输入机会");
        }

        //登陆成功 redis密码错误次数清0
        redisClient.putString(key, "0");

        return user;
    }
}
