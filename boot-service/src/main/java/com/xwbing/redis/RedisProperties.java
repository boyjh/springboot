package com.xwbing.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiangwb
 * redis属性加载配置类
 */
@Data
@ConfigurationProperties(prefix = RedisProperties.REDIS_PREFIX)
public class RedisProperties {
    public static final String REDIS_PREFIX = "redis";
    //最大连接数
    private Integer maxTotal;
    //最大空闲连接数
    private Integer maxIdle;
    //最小空闲连接数
    private Integer minIdle;
    private String host;
    private Integer port;
    private Integer timeout;
    private String password;
    //本项目缓存前缀
    private String prefix;
    //开启自动配置
    private String enabled;
}
