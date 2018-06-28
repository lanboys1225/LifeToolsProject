package com.bing.lan.holder;

import com.bing.lan.domain.CommRequestParams;

/**
 * Created by 蓝兵 on 2018/6/28.
 */

public class CommRequestParamsHolder {

    private static ThreadLocal<CommRequestParams> commRequestParamsHolder = new ThreadLocal<CommRequestParams>();

    public static CommRequestParams getCommRequestParams() {
        return commRequestParamsHolder.get();
    }

    public static void setCommRequestParams(CommRequestParams commRequestParams) {
        commRequestParamsHolder.set(commRequestParams);
    }

    public static void removeCommRequestParams() {
        commRequestParamsHolder.remove();
    }
}