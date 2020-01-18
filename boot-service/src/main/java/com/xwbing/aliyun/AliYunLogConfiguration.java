package com.xwbing.aliyun;

import com.aliyun.openservices.log.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class AliYunLogConfiguration {
    /**
     * 阿里云所属区域匹配Endpoint
     */
    private static final String ENDPOINT = "cn-hangzhou.log.aliyuncs.com";
    /**
     * 区域创建的项目名称
     */
    @Value("${aliYunLog.project}")
    private String project;
    /**
     * 项目下创建的logStore
     */
    @Value("${aliYunLog.logStore}")
    private String logStore;
    @Resource
    private Client aliYunLogClient;

    @Bean
    public Client aliYunLogClient() {
        return new Client(ENDPOINT, "xxx", "xxx");
    }

    @Bean
    public AliYunLog aliYunLog() {
        return new AliYunLog(aliYunLogClient, logStore, "springboot", project);
    }
}
