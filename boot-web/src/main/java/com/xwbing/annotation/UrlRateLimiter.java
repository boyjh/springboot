package com.xwbing.annotation;

import java.lang.annotation.*;

/**
 * @author xiangwb
 * 操作频率限制
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface UrlRateLimiter {
    /**
     * 限制时间，单位s
     * @return
     */
    int liveTime() default 60;
}
