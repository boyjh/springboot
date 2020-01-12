package com.xwbing.annotation;

import java.lang.annotation.*;

/**
 * 乐观锁重试注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Retry {
}
