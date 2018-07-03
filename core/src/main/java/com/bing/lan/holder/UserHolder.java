package com.bing.lan.holder;

/**
 * Created by 蓝兵 on 2018/6/26.
 */

public class UserHolder {

    private static ThreadLocal<Long> userIdHolder = new ThreadLocal<Long>();

    public static Long getUserId() {
        return userIdHolder.get();
    }

    public static void setUserId(Long userId) {
        userIdHolder.set(userId);
    }

    public static void removeUserId() {
        userIdHolder.remove();
    }
}
