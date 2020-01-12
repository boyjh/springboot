package com.xwbing.handler;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.annotation.Retry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.HeuristicCompletionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author xiangwb
 * @date 20/1/9 15:20
 * 乐观锁异常切面
 */
@Aspect
@Component
public class RetryAspect {
    private static final Logger logger = LoggerFactory.getLogger(RetryAspect.class);

    @Pointcut("within(com.xwbing.service..*) && @annotation(retry)")
    public void retryCut(Retry retry) {
    }

    @Around(value = "retryCut(retry)", argNames = "joinPoint,retry")
    public Object around(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception exception) {
            if (exception instanceof HeuristicCompletionException || exception instanceof StaleObjectStateException) {
                String className = joinPoint.getTarget().getClass().getSimpleName();
                String methodName = joinPoint.getSignature().getName();
                String params = Arrays.stream(joinPoint.getArgs())
                        .filter(param -> !(param instanceof HttpServletRequest || param instanceof HttpServletResponse))
                        .map(JSONObject::toJSONString).collect(Collectors.joining(","));
                logger.info("class:{} method:{} params:{}", className, methodName, params);
                return joinPoint.proceed();
            }
            throw exception;
        }
    }
}
