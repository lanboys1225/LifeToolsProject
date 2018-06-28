package com.bing.lan.project.api.interceptor;

import com.bing.lan.core.api.LogUtil;
import com.bing.lan.domain.CommRequestParams;
import com.bing.lan.holder.CommRequestParamsHolder;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 蓝兵 on 2018/6/28.
 */

public class CommRequestParamsInterceptor extends HandlerInterceptorAdapter {

    private final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        CommRequestParamsHolder.removeCommRequestParams();
        CommRequestParams commRequestParams = CommRequestParams.builder()
                .platform(request.getParameter("platform"))
                .ip(request.getParameter("ip"))
                .version(request.getParameter("version"))
                .channel(request.getParameter("channel"))
                .deviceId(request.getParameter("deviceId"))
                .build();

        log.i("preHandle() 公共请求参数: " + commRequestParams);
        CommRequestParamsHolder.setCommRequestParams(commRequestParams);

        return super.preHandle(request, response, handler);
    }
}
