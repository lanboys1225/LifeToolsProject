package com.bing.lan.project.userApi.constants;

/**
 * Created by 蓝兵 on 2018/6/25.
 */

public class Constant {

    //登录密码错误次数限制
    public static final int ERROR_PWD_NUM = 125;

    // token 过期时间 7天
    public static final long TOKEN_EXPIRE_TIME = 7 * 24 * 3600 * 1000;

    public static final String USER_ID = "user_id";
}
