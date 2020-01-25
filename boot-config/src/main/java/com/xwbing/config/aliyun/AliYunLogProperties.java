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
    public static final String PREFIX = "boot.aliYun";
    /**
     * 开启自动配置
     */
    private String enabled;
    /**
     * 阿里云日志
     */
    private Log log = new Log();
    /**
     * 钉钉机器人
     */
    private DingTalk dingTalk = new DingTalk();

    @Data
    protected static class Log {
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
    }

    @Data
    protected static class DingTalk {
        /**
         * 钉钉机器人地址
         */
        private String webHook;
        /**
         * 钉钉消息安全设置:加签
         */
        private String secret;
    }
}
