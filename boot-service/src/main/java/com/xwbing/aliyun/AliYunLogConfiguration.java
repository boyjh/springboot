package com.xwbing.aliyun;

import com.aliyun.openservices.log.Client;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Data
@Configuration
@ConditionalOnProperty(prefix = "aliYunLog", name = {"accessId", "accessKey"}, havingValue = "true")
@ConfigurationProperties(prefix = "aliYunLog")
public class AliYunLogConfiguration {
    private String endpoint;
    private String project;
    private String accessId;
    private String accessKey;
    private String topic;
    private String logStore;
    private String webHook;
    private String secret;

    @Bean
    public Client aliYunLogClient() {
        return new Client(endpoint, accessId, accessKey);
    }

    @Bean
    @ConditionalOnMissingBean(AliYunLog.class)
    public AliYunLog aliYunLog(Client aliYunLogClient) {
        return new AliYunLog(aliYunLogClient, logStore, topic, project, webHook, secret);
    }
}
