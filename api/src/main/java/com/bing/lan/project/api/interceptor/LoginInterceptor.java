package com.bing.lan.project.api.interceptor;

import com.alibaba.dubbo.common.json.JSON;
import com.bing.lan.core.api.ApiResult;
import com.bing.lan.core.api.LogUtil;
import com.bing.lan.holder.UserHolder;
import com.bing.lan.project.api.interceptor.annotation.RequiredLogin;
import com.bing.lan.project.userApi.constants.RedisConstant;
import com.bing.lan.redis.RedisClient;
import com.bing.lan.utils.JavaWebTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;

import static com.bing.lan.core.api.ApiResult.HTTP_CODE_LOGIN_REMOTE;
import static com.bing.lan.core.api.ApiResult.HTTP_CODE_LOGIN_REMOTE_MSG;
import static com.bing.lan.core.api.ApiResult.HTTP_CODE_LOGIN_TOO_LONG;
import static com.bing.lan.core.api.ApiResult.HTTP_CODE_LOGIN_TOO_LONG_MSG;
import static com.bing.lan.core.api.ApiResult.HTTP_CODE_NOT_LOGIN;
import static com.bing.lan.core.api.ApiResult.HTTP_CODE_NOT_LOGIN_MSG;
import static com.bing.lan.project.userApi.constants.Constant.USER_ID;

/**
 * Created by 蓝兵 on 2018/6/23.
 */

public class LoginInterceptor extends HandlerInterceptorAdapter {

    private final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);
    @Autowired
    private RedisClient redisClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 清除 userId
        UserHolder.removeUserId();

        // 不在拦截范围
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod o = (HandlerMethod) handler;
        RequiredLogin classRequiredLogin = o.getBean().getClass().getAnnotation(RequiredLogin.class);
        RequiredLogin methodRequiredLogin = o.getMethodAnnotation(RequiredLogin.class);

        if (classRequiredLogin != null || methodRequiredLogin != null) {
            String token = request.getParameter("token");
            Map<String, Object> map = JavaWebTokenUtil.parserJavaWebToken(token);

            if (token != null && map != null && map.size() > 0) {
                // token 验证成功
                Long expirationTime = (Long) map.get(Claims.EXPIRATION);

                if (expirationTime * 1000 - System.currentTimeMillis() > 0) {
                    //未过期
                    String userId = (String) map.get(USER_ID);
                    String redisToken = redisClient.getString(RedisConstant.REDIS_TOKEN_KEY + userId);
                    if (token.equals(redisToken)) {
                        // 最终验证通过(单点登录)
                        UserHolder.setUserId(userId);
                        return super.preHandle(request, response, handler);
                    } else {
                        // 异地登录
                        writeFail(response, HTTP_CODE_LOGIN_REMOTE, HTTP_CODE_LOGIN_REMOTE_MSG);
                        log.i("preHandle():  " + HTTP_CODE_LOGIN_REMOTE_MSG);
                        return false;
                    }
                } else {
                    // 过期
                    writeFail(response, HTTP_CODE_LOGIN_TOO_LONG, HTTP_CODE_LOGIN_TOO_LONG_MSG);
                    log.i("preHandle():  " + HTTP_CODE_LOGIN_TOO_LONG_MSG);
                    return false;
                }
            }

            writeFail(response, HTTP_CODE_NOT_LOGIN, HTTP_CODE_NOT_LOGIN_MSG);
            //todo 根据 RequiredLogin value 判断返回json 还是 html；
            log.i("preHandle():  " + HTTP_CODE_NOT_LOGIN_MSG);
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
