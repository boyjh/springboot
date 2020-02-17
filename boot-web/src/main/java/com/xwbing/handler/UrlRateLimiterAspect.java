package com.xwbing.handler;

import com.xwbing.annotation.UrlRateLimiter;
import com.xwbing.config.redis.RedisService;
import com.xwbing.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;

/**
 * @author xiangwb
 * @date 20/2/17 15:51
 * 操作频率校验
 */
@Slf4j
@Aspect
@Component
public class UrlRateLimiterAspect {
    @Resource
    private RedisService redisService;


    @Pointcut("@annotation(urlRateLimiter)")
    public void beforePointCut(UrlRateLimiter urlRateLimiter) {
    }

    @Pointcut("@annotation(com.xwbing.annotation.UrlRateLimiter)")
    public void afterPointCut() {
    }

    /**
     * 添加操作频率缓存
     */
    @Before(value = "beforePointCut(urlRateLimiter)", argNames = "urlRateLimiter")
    public void before(UrlRateLimiter urlRateLimiter) {
        String key = getKey();
        Long num = redisService.incr(key);
        if (num > 1) {
            throw new BusinessException("操作过于频繁，请稍后再试");
        }
        redisService.expire(key, urlRateLimiter.liveTime());
    }

    /**
     * 删除操作频率缓存
     */
    @After("afterPointCut()")
    public void after() {
        String key = getKey();
        redisService.del(key);
    }

    private String getKey() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String requestURI = attributes.getRequest().getRequestURI();
        String userId = attributes.getRequest().getHeader("userId");
        //操作限制
        return "limit-" + requestURI + "-" + userId;
    }
}
