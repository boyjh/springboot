package com.xwbing.annotation;

import java.lang.annotation.*;

/**
 * @author xiangwb
 * @date 20/1/15 20:30
 * 并发锁注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Lock {

    String value(); //SpEL表达式

    String remark() default "lock failed, please try again later";

    String operator() default "";

    int timeout() default 10; //seconds
}
