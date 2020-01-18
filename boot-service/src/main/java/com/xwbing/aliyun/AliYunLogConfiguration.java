package com.xwbing.aliyun;

import com.aliyun.openservices.log.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;


@Configuration
public class AliYunLogConfiguration {

    @Resource
    private Client aliYunLogClient;

    @Value("${aliYun.logStore}")
    private String logStore;

    @Bean
    public Client aliYunLogClient() {
        return new Client("cn-hangzhou.log.aliyuncs.com", "xxx", "xxx");
    }

    @Bean
    public AliYunLog getLeaseAliYunLog() {
        return new AliYunLog(aliYunLogClient, logStore, "springboot");
    }
}
