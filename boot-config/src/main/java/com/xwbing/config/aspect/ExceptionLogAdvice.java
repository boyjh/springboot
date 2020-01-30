package com.xwbing.config.aspect;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author xiangwb
 * 异常记录通知
 * rpc调用中台服务异常日志记录,便于排查问题(GlobalExceptionHandler无法捕捉异常)
 */
@Slf4j
public class ExceptionLogAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        try {
            return mi.proceed();
        } catch (Throwable exception) {
            Method method = mi.getMethod();
            String className = method.getDeclaringClass().getSimpleName();
            String methodName = method.getName();
            String params = Arrays.stream(mi.getArguments())
                    .filter(param -> !(param instanceof HttpServletRequest || param instanceof HttpServletResponse))
                    .map(JSONObject::toJSONString).collect(Collectors.joining(","));
            String stackTrace = ExceptionUtils.getStackTrace(exception);
            if (StringUtils.isNotEmpty(params)) {
                log.error("{}.{} - params:{} - exception:{}", className, methodName, params, stackTrace);
            } else {
                log.error("{}.{} - exception:{}", className, methodName, stackTrace);
            }
            throw exception;
        }
    }
}
