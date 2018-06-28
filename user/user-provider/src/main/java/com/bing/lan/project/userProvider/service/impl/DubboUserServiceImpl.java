package com.bing.lan.project.userProvider.service.impl;

import com.bing.lan.core.api.LogUtil;
import com.bing.lan.domain.CommRequestParams;
import com.bing.lan.domain.QueryDomain;
import com.bing.lan.project.userApi.constants.Constant;
import com.bing.lan.project.userApi.constants.RedisConstant;
import com.bing.lan.project.userApi.domain.ResetPasswordResult;
import com.bing.lan.project.userApi.domain.User;
import com.bing.lan.project.userApi.domain.UserLog;
import com.bing.lan.project.userApi.exception.UserException;
import com.bing.lan.project.userApi.service.DubboUserService;
import com.bing.lan.project.userProvider.mapper.UserLogMapper;
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
import java.util.List;
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
    private UserLogMapper userLogMapper;

    /**
     * 根据手机号查询用户
     */
    private User selectByPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            throw new UserException("请填写手机号");
        }
        return userMapper.selectByPhone(phone);
    }

    /**
     * 根据userId查询用户
     */
    private User selectByPrimaryKey(String id) {
        if (StringUtils.isBlank(id)) {
            throw new UserException("参数异常");
        }
        return userMapper.selectByPrimaryKey(Long.valueOf(id));
    }

    @Override
    public User doRegister(CommRequestParams commRequestParams, String phone,
            String password, String nickName, String userName) {

        User user = selectByPhone(phone);

        UserLog userLog = UserLog.builder().build();
        userLog.setLogType("register");
        userLog.setPhone(phone);

        userLog.setIp(commRequestParams.getIp());
        userLog.setChannel(commRequestParams.getChannel());
        userLog.setPlatform(commRequestParams.getPlatform());
        userLog.setDeviceId(commRequestParams.getDeviceId());
        userLog.setVersion(commRequestParams.getVersion());

        if (user != null) {
            userLog.setUserId(user.getId());
            userLog.setComment("用户早已存在");
            userLogMapper.insert(userLog);
            throw new UserException(userLog.getComment());
        }
        user = User.builder()
                .userName(userName)
                .nickname(nickName)
                .password(password)
                .phone(phone)
                .build();

        userMapper.insert(user);
        userLog.setUserId(user.getId());
        userLogMapper.insert(userLog);

        return user;
    }

    @Override
    public ResetPasswordResult resetLoginPassword(CommRequestParams commRequestParams, String phone,
            String password, String newPassword) {

        User user = selectByPhone(phone);

        UserLog userLog = UserLog.builder().build();
        userLog.setLogType("resetPassword");
        userLog.setPhone(phone);

        userLog.setIp(commRequestParams.getIp());
        userLog.setChannel(commRequestParams.getChannel());
        userLog.setPlatform(commRequestParams.getPlatform());
        userLog.setDeviceId(commRequestParams.getDeviceId());
        userLog.setVersion(commRequestParams.getVersion());

        if (user == null) {
            userLog.setComment("用户不存在");
            userLogMapper.insert(userLog);
            throw new UserException(userLog.getComment());
        }

        userLog.setUserId(user.getId());
        if (StringUtils.isBlank(password) || StringUtils.isBlank(newPassword)) {
            userLog.setComment("参数异常");
            userLogMapper.insert(userLog);
            throw new UserException(userLog.getComment());
        }
        if (!password.equals(user.getPassword())) {
            userLog.setComment("密码不正确");
            userLogMapper.insert(userLog);
            throw new UserException(userLog.getComment());
        }
        user.setPassword(newPassword);
        userMapper.updateByPrimaryKey(user);
        userLogMapper.insert(userLog);
        userLogMapper.insert(userLog);

        return ResetPasswordResult.builder().isResetSuccess(true).build();
    }

    @Override
    public QueryDomain<UserLog> userLog(String userId, QueryDomain<UserLog> queryDomain) {
        List<UserLog> userLogs = userLogMapper.selectAllByUserId(userId,
                queryDomain.getOffset(), queryDomain.getPageSize());

        queryDomain.setList(userLogs);
        queryDomain.setTotalSize(userLogMapper.countByUserId(userId));

        return queryDomain;
    }

    @Override
    public User doLogin(CommRequestParams commRequestParams, String phone, String password) {

        User user = selectByPhone(phone);
        UserLog userLog = UserLog.builder().build();

        userLog.setLogType("login");
        userLog.setPhone(phone);

        userLog.setIp(commRequestParams.getIp());
        userLog.setChannel(commRequestParams.getChannel());
        userLog.setPlatform(commRequestParams.getPlatform());
        userLog.setDeviceId(commRequestParams.getDeviceId());
        userLog.setVersion(commRequestParams.getVersion());

        if (user == null) {
            userLog.setLoginStatus("2");
            userLog.setComment("用户不存在");
            userLogMapper.insert(userLog);
            throw new UserException(userLog.getComment());
        }

        userLog.setUserName(user.getUserName());
        userLog.setUserId(user.getId());

        if ("Y".equalsIgnoreCase(user.getIsDelete())) {
            userLog.setLoginStatus("3");
            userLog.setComment("用户被删除");
            userLogMapper.insert(userLog);
            throw new UserException(userLog.getComment());
        }

        if (StringUtils.isBlank(user.getPassword())) {
            userLog.setLoginStatus("4");
            userLog.setComment("您还未设置登录密码，请重置密码");
            userLogMapper.insert(userLog);
            throw new UserException(userLog.getComment());
        }

        String key = RedisConstant.REDIS_PWD_ERROR_NUM_KEY + user.getId();

        String errorNum = redisClient.getString(key);
        Integer num = 0;

        if (!StringUtils.isBlank(errorNum)) {
            num = Integer.parseInt(errorNum);
            if (num >= Constant.ERROR_PWD_NUM) {
                userLog.setLoginStatus("6");
                userLog.setComment("您输入的密码错误，今日机会已用完，请找回密码");
                userLogMapper.insert(userLog);
                throw new UserException(userLog.getComment());
            }
        }

        if (!user.getPassword().equals(password)) {

            //计算距离当天结束时间
            long milliSecondsLeftToday = 86400000 -
                    DateUtils.getFragmentInMilliseconds(Calendar.getInstance(), Calendar.DATE);

            redisClient.putString(key, (num + 1) + "", milliSecondsLeftToday / 1000);
            userLog.setLoginStatus("5");
            userLog.setComment("您输入的密码错误，今日还有 " + (Constant.ERROR_PWD_NUM - num - 1) + "次输入机会");
            userLogMapper.insert(userLog);
            throw new UserException(userLog.getComment());
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

        userLog.setToken(token);
        userLog.setTokenExpireTime(date);

        userLog.setLoginStatus("0");
        userLogMapper.insert(userLog);

        return user;
    }
}
