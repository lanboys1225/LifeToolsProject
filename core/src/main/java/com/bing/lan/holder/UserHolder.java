package com.bing.lan.holder;

/**
 * Created by 蓝兵 on 2018/6/26.
 */

public class UserHolder {

    private static ThreadLocal<String> userIdHolder = new ThreadLocal<String>();

    public static String getUserId() {
        return userIdHolder.get();
    }

    public static void setUserId(String userId) {
        userIdHolder.set(userId);
    }

    public static void removeUserId() {
        userIdHolder.remove();
    }
}
