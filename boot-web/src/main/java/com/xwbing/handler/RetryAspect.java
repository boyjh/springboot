package com.xwbing.handler;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.StaleObjectStateException;
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
@Slf4j
@Aspect
@Component
@Order
public class RetryAspect {
    //阿里巴巴java开发手册建议乐观锁重试次数不得小于3次
    private static final int MAX_RETRY = 3;

    @Pointcut("@annotation(com.xwbing.annotation.Retry)")
    public void retryCut() {
    }

    @Around("retryCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        int retry = 0;
        Exception optimisticLockException;
        do {
            retry++;
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
                    optimisticLockException = exception;
                    log.info("class:{} method:{} params:{}", className, methodName, params);
                } else {
                    throw exception;
                }
            }
        } while (retry < MAX_RETRY);
        throw optimisticLockException;
    }
}
