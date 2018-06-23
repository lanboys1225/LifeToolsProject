package com.bing.lan.exception;

/**
 * Created by 蓝兵 on 2018/6/21.
 * <p>
 * https://www.jianshu.com/p/1e2245fe5ea6
 * https://blog.csdn.net/xlee1905/article/details/44660449
 */

public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }
}
