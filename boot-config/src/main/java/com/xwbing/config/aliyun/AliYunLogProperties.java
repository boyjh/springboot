package com.xwbing.config.aliyun;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiangwb
 * @date 20/1/21 10:10
 */
@Data
@ConfigurationProperties(prefix = AliYunLogProperties.PREFIX)
public class AliYunLogProperties {
    public static final String PREFIX = "aliYunLog";
    private String accessId;
    private String accessKey;
    /**
     * 华东1(杭州)
     */
    private String endpoint;
    /**
     * 阿里云上创建的项目名称
     */
    private String project;
    /**
     * 建议填本项目名称
     */
    private String topic;
    /**
     * 日志库名称
     */
    private String logStore;
    /**
     * 钉钉机器人地址
     */
    private String webHook;
    /**
     * 钉钉消息安全设置:加签
     */
    private String secret;
}
