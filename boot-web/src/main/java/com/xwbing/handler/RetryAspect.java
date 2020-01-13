package com.xwbing.handler;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;
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
@Order
public class RetryAspect {
    private static final Logger logger = LoggerFactory.getLogger(RetryAspect.class);

    @Pointcut("@annotation(com.xwbing.annotation.Retry)")
    public void retryCut() {
    }

    @Around("retryCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception exception) {
            if (exception instanceof HeuristicCompletionException
                    || exception instanceof HibernateOptimisticLockingFailureException
                    || exception instanceof StaleObjectStateException) {
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
