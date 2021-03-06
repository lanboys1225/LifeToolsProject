package com.bing.lan.project.userProvider;

import com.bing.lan.core.api.LogUtil;
import com.bing.lan.project.userApi.DubboUserService;
import com.bing.lan.project.userApi.domain.User;
import com.bing.lan.project.userApi.exception.UserException;
import com.bing.lan.project.userProvider.mapper.UserMapper;
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

    @Autowired
    private UserMapper userMapper;

    public User doLogin(String phone, String password) {

        try {
            String id = redisClient.getString("user_id");
            if (id == null) {
                redisClient.putString("user_id", "1");
            } else {
                Integer integer = Integer.valueOf(id);
                redisClient.putString("user_id", String.valueOf(++integer));
            }

            log.i("doLogin() redis 缓存缓存缓存缓存缓存缓存缓存缓存 user_id: " + id);
        } catch (Exception e) {
            log.e("doLogin() redis 缓存缓存缓存缓存缓存缓存缓存异常：" + e.getLocalizedMessage());
        }

        User user = userMapper.selectByPhoneAndPassword(phone, password);
        if (user == null) {
            throw new UserException("账号或密码错误");
        }

        return user;
    }
}
