package com.bing.lan.rpc;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.bing.lan.core.api.LogUtil;

import java.util.UUID;

/**
 * Description: dubbo服务请求响应日志
 */
@Activate(group = {Constants.CONSUMER, Constants.PROVIDER})
public class RpcTraceLogFilter implements Filter {

    private static final String RPC_TRACE_ID = "rpc_trace_id";

    private final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String serviceSide = getServiceSide();
        log.i("-------------↓↓↓↓↓↓↓↓↓↓↓ " + serviceSide + " dubbo begin ↓↓↓↓↓↓↓↓↓↓↓↓↓-------------");
        long start = System.currentTimeMillis();
        Result result = null;
        String traceId = getTraceId(invocation);
        try {
            log.i("dubbo thread:" + Thread.currentThread().getName());
            log.i("dubbo traceId：" + traceId);
            log.i("dubbo remote host：" + RpcContext.getContext().getRemoteHost());
            log.i("dubbo method：" + invoker.getInterface() + "." + invocation.getMethodName());
            log.i("dubbo params：" + JSON.toJSONString(invocation.getArguments()));
            log.i("----------------------------------------------");
            result = invoker.invoke(invocation);
            log.i("----------------------------------------------");
        } catch (RpcException ex) {
            log.e("dubbo RpcException, traceId：" + traceId, ex);
            throw ex;
        } finally {
            if (result != null && result.hasException()) {
                log.e("dubbo result exception, traceId：" + traceId, result.getException());
            } else {
                log.i("dubbo result：" + JSON.toJSONString(result == null ? "" : result.getValue()));
            }
        }
        log.i("dubbo耗时：" + (System.currentTimeMillis() - start) + "ms");
        log.i("-------------↑↑↑↑↑↑↑↑↑↑↑↑ " + serviceSide + " dubbo end ↑↑↑↑↑↑↑↑↑↑↑↑-------------");
        return result;
    }

    private String getTraceId(Invocation invocation) {
        String traceId = RpcTraceHolder.getTraceId();
        if (traceId == null || "".equals(traceId.trim())) {
            //下游服务 从 Invocation 获取 traceId
            traceId = invocation.getAttachment(RPC_TRACE_ID);
            if (traceId == null || "".equals(traceId.trim())) {
                traceId = UUID.randomUUID().toString();
                log.i("dubbo create traceId：" + traceId);
                RpcTraceHolder.setTraceId(traceId);
            }
        }
        // 设置 traceId 下游 服务可从 Invocation 获取
        RpcContext.getContext().setAttachment(RPC_TRACE_ID, traceId);
        return traceId;
    }

    private String getServiceSide() {
        String side = RpcContext.getContext().getUrl().getParameter(Constants.SIDE_KEY);
        if (Constants.CONSUMER.equals(side)) {
            return "调用下游";
        }
        return "响应上游";
    }
}
