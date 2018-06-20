package com.bing.lan.project.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bing.lan.core.api.LogUtil;
import com.bing.lan.rpc.RpcTraceHolder;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Description: 日志拦截记录
 * <p>
 */
@Component
@Aspect
public class LogAspect {

    private final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);

    @Pointcut("execution(public * com.bing.lan.project.api.controller.*.*(..))")
    public void webLog() {
    }

    @Around("webLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.i("-------------↓↓↓↓↓↓↓↓↓↓↓↓ request begin ↓↓↓↓↓↓↓↓↓↓↓↓-------------");
        Object result = null;
        long start = System.currentTimeMillis();
        begin();
        try {
            result = pjp.proceed();
            return result;
        } catch (Throwable throwable) {
            rollback(throwable);
            throw throwable;
        } finally {
            finallyInvoke(result);
            log.i("总耗时：" + (System.currentTimeMillis() - start) + "ms");
            log.i("-------------↑↑↑↑↑↑↑↑↑↑↑↑ request end ↑↑↑↑↑↑↑↑↑↑↑↑-------------");
        }
    }

    public void begin() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            String traceId = UUID.randomUUID().toString();
            RpcTraceHolder.setTraceId(traceId);

            log.i("create traceId：" + traceId);
            log.i("thread: " + Thread.currentThread().getName());
            log.i("request ip：" + getIpAddr(request));
            log.i("request url：" + request.getRequestURL());
            log.i("request method：" + request.getMethod());
            log.i("request params：" + JSONObject.toJSON(request.getParameterMap()));
            log.i("----------------------------------------------");
        } catch (Exception e) {
            log.e("log begin exception：", e);
        }
    }

    public void finallyInvoke(Object result) {
        try {
            String resultStr = JSON.toJSONString(result == null ? "" : result);
            if (resultStr.length() > 200) {
                resultStr = resultStr.substring(0, 200);
            }

            log.i("traceId：" + RpcTraceHolder.getTraceId());
            log.i("thread: " + Thread.currentThread().getName());
            log.i("result：" + resultStr);
        } catch (Exception e) {
            log.e("log finallyInvoke exception：", e);
        }
    }

    public void rollback(Throwable throwable) {
        //throwable.printStackTrace();
        log.e("请求异常, traceId：" + RpcTraceHolder.getTraceId(), throwable);
    }

    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }
}
