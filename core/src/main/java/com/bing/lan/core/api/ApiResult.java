package com.bing.lan.core.api;

import com.google.gson.Gson;

/**
 * 统一处理返回数据
 */
public class ApiResult<T> {

    public static final int HTTP_CODE_SUCCESS = 200;
    public static final int HTTP_CODE_FAIL = 500;
    public static final int HTTP_CODE_SERVICE_EXCEPTION = 501;

    public static final String HTTP_CODE_SUCCESS_MSG = "系统繁忙! 请稍后再试.. ";
    public static final String HTTP_CODE_FAIL_MSG = "请求成功";

    private int code = HTTP_CODE_SUCCESS;

    private String msg = HTTP_CODE_SUCCESS_MSG;

    private T data;

    public static <T> ApiResult<T> objectFromData(String str) {

        return new Gson().fromJson(str, ApiResult.class);
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
}
