package com.bing.lan.core.api;

import com.google.gson.Gson;

import java.util.Date;

/**
 * 统一处理返回数据
 */
public class ApiResult<T> {

    public static final int HTTP_CODE_SUCCESS = 200;
    public static final int HTTP_CODE_FAIL = 500;
    public static final int HTTP_CODE_SERVICE_EXCEPTION = 501;
    public static final int HTTP_CODE_NOT_LOGIN = 600;
    public static final int HTTP_CODE_LOGIN_TOO_LONG = 601;
    public static final int HTTP_CODE_LOGIN_REMOTE = 602;

    public static final String HTTP_CODE_SUCCESS_MSG = "请求成功";
    public static final String HTTP_CODE_FAIL_MSG = "系统繁忙! 请稍后再试.. ";
    public static final String HTTP_CODE_NOT_LOGIN_MSG = "请先登录";
    public static final String HTTP_CODE_LOGIN_TOO_LONG_MSG = "登录时间过长，请重新登录";
    public static final String HTTP_CODE_LOGIN_REMOTE_MSG ="您已在别的设备登录，请重新登录";

    private int code = HTTP_CODE_SUCCESS;

    private String msg = HTTP_CODE_SUCCESS_MSG;

    private T data;

    private Date time;

    public static <T> ApiResult<T> objectFromData(String str) {

        return new Gson().fromJson(str, ApiResult.class);
    }

    public ApiResult() {
    }

    public ApiResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
