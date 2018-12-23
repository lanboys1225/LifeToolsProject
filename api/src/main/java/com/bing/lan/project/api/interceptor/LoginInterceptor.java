package com.bing.lan.project.api.interceptor;

import com.alibaba.dubbo.common.json.JSON;
import com.bing.lan.core.api.ApiResult;
import com.bing.lan.core.api.LogUtil;
import com.bing.lan.project.api.interceptor.annotation.RequiredLogin;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.bing.lan.core.api.ApiResult.HTTP_CODE_NOT_LOGIN;
import static com.bing.lan.core.api.ApiResult.HTTP_CODE_NOT_LOGIN_MSG;

/**
 * Created by 蓝兵 on 2018/6/23.
 */

public class LoginInterceptor extends HandlerInterceptorAdapter {

    private final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 不在拦截范围
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod o = (HandlerMethod) handler;
        RequiredLogin classRequiredLogin = o.getBean().getClass().getAnnotation(RequiredLogin.class);
        RequiredLogin methodRequiredLogin = o.getMethodAnnotation(RequiredLogin.class);
        boolean isRequiredLogin = classRequiredLogin != null || methodRequiredLogin != null;
        //long l = Long.valueOf(request.getParameter("password")) % 2;
        if (/*l == 0 && */isRequiredLogin) {
            writeFail(response, HTTP_CODE_NOT_LOGIN, HTTP_CODE_NOT_LOGIN_MSG);
            //todo 根据 RequiredLogin value 判断返回json 还是 html；
            log.i("preHandle(): 未登录");
            return false;
        }

        return super.preHandle(request, response, handler);
    }

    public static void writeFail(HttpServletResponse httpServletResponse, int code, String message) throws Exception {
        httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
        ApiResult<Object> apiResult = new ApiResult<Object>();
        apiResult.setCode(code);
        apiResult.setMsg(message);
        apiResult.setTime(new Date());
        httpServletResponse.getWriter().write(JSON.json(apiResult));
    }
}
