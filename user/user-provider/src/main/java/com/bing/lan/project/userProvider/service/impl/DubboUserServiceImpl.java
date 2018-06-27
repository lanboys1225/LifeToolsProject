package com.bing.lan.project.userProvider.service.impl;

import com.bing.lan.core.api.LogUtil;
import com.bing.lan.project.userApi.constants.Constant;
import com.bing.lan.project.userApi.constants.RedisConstant;
import com.bing.lan.project.userApi.domain.LoginLog;
import com.bing.lan.project.userApi.domain.User;
import com.bing.lan.project.userApi.exception.UserException;
import com.bing.lan.project.userApi.service.DubboUserService;
import com.bing.lan.project.userProvider.mapper.LoginLogMapper;
import com.bing.lan.project.userProvider.mapper.UserMapper;
import com.bing.lan.redis.RedisClient;
import com.bing.lan.utils.JavaWebTokenUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;

import static com.bing.lan.project.userApi.constants.Constant.TOKEN_EXPIRE_TIME;
import static com.bing.lan.project.userApi.constants.Constant.USER_ID;

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

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public User doRegister(String phone, String password, String nickName, String userName,
            String version, String deviceId, String platform, String channel, String ip) {

        User user = userMapper.selectByPhone(phone);
        if (user != null) {
            throw new UserException("用户早已存在");
        }
        if (StringUtils.isBlank(phone)) {
            throw new UserException("请填写手机号");
        }

        user = User.builder()
                .userName(userName)
                .nickname(nickName)
                .password(password)
                .phone(phone)
                .build();

        userMapper.insert(user);
        return user;
    }

    @Override
    public User doLogin(String phone, String password, String version, String deviceId,
            String platform, String channel, String ip) {

        User user = userMapper.selectByPhone(phone);
        LoginLog loginLog = LoginLog.builder().build();

        loginLog.setPhone(phone);
        loginLog.setDate(new Date());
        loginLog.setIp(ip);
        loginLog.setChannel(channel);
        loginLog.setPlatform(platform);
        loginLog.setDeviceId(deviceId);
        loginLog.setVersion(version);

        if (user == null) {
            loginLog.setStatus("2");
            loginLogMapper.insert(loginLog);
            throw new UserException("用户不存在");
        }

        loginLog.setUserName(user.getUserName());
        loginLog.setUserId(user.getId());

        if ("Y".equalsIgnoreCase(user.getIsDelete())) {
            loginLog.setStatus("3");
            loginLogMapper.insert(loginLog);
            throw new UserException("用户被删除");
        }

        if (StringUtils.isBlank(user.getPassword())) {
            loginLog.setStatus("4");
            loginLogMapper.insert(loginLog);
            throw new UserException("您还未设置登录密码，请重置密码");
        }

        String key = RedisConstant.REDIS_PWD_ERROR_NUM_KEY + user.getId();

        String errorNum = redisClient.getString(key);
        Integer num = 0;

        if (!StringUtils.isBlank(user.getPassword())) {
            num = Integer.parseInt(errorNum);
            if (num >= Constant.ERROR_PWD_NUM) {
                loginLog.setStatus("6");
                loginLogMapper.insert(loginLog);
                throw new UserException("您输入的密码错误，今日机会已用完，请找回密码");
            }
        }

        if (!user.getPassword().equals(password)) {

            //计算距离当天结束时间
            long milliSecondsLeftToday = 86400000 -
                    DateUtils.getFragmentInMilliseconds(Calendar.getInstance(), Calendar.DATE);

            redisClient.putString(key, (num + 1) + "", milliSecondsLeftToday / 1000);
            loginLog.setStatus("5");
            loginLogMapper.insert(loginLog);

            throw new UserException("您输入的密码错误，今日还有" + (Constant.ERROR_PWD_NUM - num - 1) + "次输入机会");
        }

        //登陆成功 redis密码错误次数清0
        redisClient.putString(key, "0");

        // 过期时间
        Date date = new Date(System.currentTimeMillis() + TOKEN_EXPIRE_TIME);
        Map<String, Object> map = new HashMap<>();
        map.put(Claims.EXPIRATION, date);
        map.put(USER_ID, String.valueOf(user.getId()));
        // 生成token
        String token = JavaWebTokenUtil.createJavaWebToken(map);

        redisClient.putString(RedisConstant.REDIS_TOKEN_KEY + user.getId(), token);

        loginLog.setToken(token);
        loginLog.setTokenExpireTime(date);

        loginLog.setStatus("0");
        loginLogMapper.insert(loginLog);

        return user;
    }
}
