package com.xwbing.handler;

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
 * controller层频率校验
 */
@Slf4j
@Aspect
@Component
public class LimitAspect {
    @Resource
    private RedisService redisService;

    @Pointcut("within(com.xwbing.controller..*)")
    public void pointCut() {
    }

    /**
     * 添加频率缓存
     */
    @Before("pointCut()")
    public void before() {
        String key = getKey();
        Long num = redisService.incr(key);
        if (num > 1) {
            throw new BusinessException("操作过于频繁，请稍后再试");
        }
        redisService.expire(key, 60);
    }

    /**
     * 删除频率缓存
     */
    @After("pointCut()")
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
