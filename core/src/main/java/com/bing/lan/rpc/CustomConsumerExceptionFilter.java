package com.bing.lan.rpc;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcResult;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by 蓝兵 on 2018/6/23.
 */

@Activate(group = Constants.CONSUMER, value = {"CustomConsumerExceptionFilter"})
public class CustomConsumerExceptionFilter implements Filter {

    static final String EXCEPTION_SPLIT_STRING = "#@%&-#";

    private static Logger logger = LoggerFactory.getLogger(CustomConsumerExceptionFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = invoker.invoke(invocation);
        if (result.hasException()) {
            Throwable exception = result.getException();
            if ((exception instanceof RuntimeException)) {
                //可能是自定义异常
                String message = exception.getMessage();
                if (exception.getMessage() != null) {
                    String[] messages = message.split(EXCEPTION_SPLIT_STRING, 2);
                    if (messages.length == 2) {
                        try {
                            //logger.debug("message:{}", message);
                            Class exceptionClass = Class.forName(messages[0]);
                            try {
                                Constructor messageConstructor = exceptionClass.getConstructor(String.class);
                                messageConstructor.setAccessible(true);
                                exception = (Throwable) messageConstructor.newInstance(messages[1]);
                            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                                try {
                                    logger.debug(e.getMessage(), e);
                                    Constructor constructor = exceptionClass.getConstructor();
                                    constructor.setAccessible(true);
                                    exception = (Throwable) constructor.newInstance();
                                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e1) {
                                    logger.debug(e1.getMessage(), e1);
                                }
                            }
                            if (result instanceof RpcResult) {
                                ((RpcResult) result).setException(exception);
                            }
                        } catch (ClassNotFoundException e) {
                            logger.warn(e.getMessage(), e);
                        }
                    }
                }
            }
        }
        return result;
    }
}
