package com.xwbing.config.aliyun;

import com.aliyun.openservices.log.Client;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@ConditionalOnProperty(prefix = AliYunLogProperties.PREFIX, name = {"accessId", "accessKey"}, havingValue = "true")
@EnableConfigurationProperties(AliYunLogProperties.class)
public class AliYunLogAutoConfiguration {
    @Resource
    private AliYunLogProperties aliYunLogProperties;

    @Bean
    @ConditionalOnMissingBean(Client.class)
    public Client aliYunLogClient() {
        return new Client(aliYunLogProperties.getEndpoint(),
                aliYunLogProperties.getAccessId(), aliYunLogProperties.getAccessKey());
    }

    @Bean
    @ConditionalOnMissingBean(AliYunLog.class)
    public AliYunLog aliYunLog(Client aliYunLogClient) {
        return new AliYunLog(aliYunLogClient, aliYunLogProperties.getLogStore(), aliYunLogProperties.getTopic(),
                aliYunLogProperties.getProject(), aliYunLogProperties.getWebHook(), aliYunLogProperties.getSecret());
    }
}
