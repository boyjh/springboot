package com.xwbing.redis;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author xiangwb
 * @date 2019/6/21 22:41
 * @description 开启redis自动配置
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RedisAutoConfiguration.class)
public @interface EnableRedis {
}
