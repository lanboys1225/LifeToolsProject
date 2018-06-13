package com.bing.lan.rpc;

/**
 * RPC调用轨迹id
 */
public class RpcTraceHolder {

    // 调用链id
    private static final ThreadLocal<String> traceIdLocal = new ThreadLocal<String>();

    public static void setTraceId(String traceId) {
        traceIdLocal.set(traceId);
    }

    public static String getTraceId() {
        return traceIdLocal.get();
    }
}
