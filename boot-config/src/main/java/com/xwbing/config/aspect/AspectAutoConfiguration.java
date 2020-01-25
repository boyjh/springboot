package com.xwbing.config.aspect;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiangwb
 * @date 20/1/21 11:06
 * 切面自动配置类
 */
@Configuration
public class AspectAutoConfiguration {
    /**
     * 基于redis分布式锁
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(LockAspect.class)
    public LockAspect lockAspect() {
        return new LockAspect();
    }

    /**
     * 乐观锁重试机制
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(OptimisticLockRetryAspect.class)
    public OptimisticLockRetryAspect optimisticLockRetryAspect() {
        return new OptimisticLockRetryAspect();
    }

    /**
     * 令牌桶限流
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(FlowLimiterAspect.class)
    public FlowLimiterAspect flowLimiterAspect() {
        return new FlowLimiterAspect();
    }

    /**
     * 幂等校验
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(IdempotentAspect.class)
    public IdempotentAspect idempotentAspect() {
        return new IdempotentAspect();
    }
}
