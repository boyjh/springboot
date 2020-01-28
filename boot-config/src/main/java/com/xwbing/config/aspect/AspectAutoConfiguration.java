package com.xwbing.config.aspect;

import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
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
    @Value("${pointcut.service}")
    private String servicePointcut;

    /**
     * service异常日志切面
     * 适用于rpc远程调用中台服务异常记录(GlobalExceptionHandler无法捕捉异常)
     *
     * @return
     */
    @Bean
    @ConditionalOnExpression("!'${pointcut.service}'.empty && !'${pointcut.service:null}'.equals('null')")
    public AspectJExpressionPointcutAdvisor afterThrowingAdvice() {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(servicePointcut);
        advisor.setAdvice(new ExceptionLogAdvice());
        return advisor;
    }

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
