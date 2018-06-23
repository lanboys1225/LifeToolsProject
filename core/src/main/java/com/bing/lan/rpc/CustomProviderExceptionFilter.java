package com.bing.lan.rpc;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ReflectUtils;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcResult;
import com.alibaba.dubbo.rpc.service.GenericService;

import java.lang.reflect.Method;

import static com.bing.lan.rpc.CustomConsumerExceptionFilter.EXCEPTION_SPLIT_STRING;

/**
 * Created by 蓝兵 on 2018/6/23.
 *
 * https://www.jianshu.com/p/1e2245fe5ea6
 */

@Activate(group = Constants.PROVIDER, before = {"exception"}, value = {"CustomProviderExceptionFilter"})
public class CustomProviderExceptionFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(CustomProviderExceptionFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = invoker.invoke(invocation);
        if (result.hasException() && GenericService.class != invoker.getInterface()) {
            try {
                Throwable exception = result.getException();
                // 如果是checked异常，直接抛出
                if (!(exception instanceof RuntimeException) && (exception instanceof Exception)) {
                    return result;
                }
                // 如果是unchecked异常, 需要方法检查签名上是否有声明
                // unchecked异常在方法签名上有声明, 意味着调用方，也存在该异常类，可反序列化，直接抛出
                try {
                    Method method = invoker.getInterface().getMethod(invocation.getMethodName(), invocation.getParameterTypes());
                    Class<?>[] exceptionClasses = method.getExceptionTypes();
                    for (Class<?> exceptionClass : exceptionClasses) {
                        if (exception.getClass().equals(exceptionClass)) {
                            return result;
                        }
                    }
                } catch (NoSuchMethodException e) {
                    return result;
                }

                // unchecked异常在方法签名上没有声明，则通过 接口类字节码 与 异常类字节码 是否同处于 一个URL下来判断，是直接抛出
                String serviceFile = ReflectUtils.getCodeBase(invoker.getInterface());
                String exceptionFile = ReflectUtils.getCodeBase(exception.getClass());
                if (serviceFile == null || exceptionFile == null || serviceFile.equals(exceptionFile)) {
                    return result;
                }

                // 是JDK自带的异常，直接抛出
                String className = exception.getClass().getName();
                if (className.startsWith("java.") || className.startsWith("javax.")) {
                    return result;
                }
                // 是Dubbo本身的异常，直接抛出
                if (exception instanceof RpcException) {
                    return result;
                }
                // 其他 exception，减少问题，直接将 exception 序列化成 RuntimeException 抛给客户端，
                // RuntimeException 可直接抛出，字节码不处于同一个URL，见上解释，但是其子类 不能直接抛出
                result = new RpcResult(new RuntimeException(exception.getClass().getName() + EXCEPTION_SPLIT_STRING + exception.getMessage()));
            } catch (Throwable e) {
                logger.error(e.getMessage(), e);
                return result;
            }
        }
        return result;
    }
}
