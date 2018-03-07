package com.xwbing.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.Filter;

/**
 * 说明: 程序上下文配置
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
@Configuration//相当于.xml文件中的<beans></beans>
//@Import(xxx.class)//用来导入其他配置类
//@ImportResource("classpath:applicationContext.xml")//用来加载其他xml配置文件
public class ApplicationContextConfig {
    /**
     * 文件上传解析器
     *
     * @return
     */
    @Bean
    public CommonsMultipartResolver getCommonsMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(104857600);
        multipartResolver.setDefaultEncoding("UTF-8");
        return multipartResolver;
    }

    /**
     * encoding编码问题(springBoot默认已经配置好)
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(CharacterEncodingFilter.class)
    public Filter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

    /**
     * 任务线程池
     *
     * @return
     */
    @Bean(name = "taskExecutor")//相当于XML中的<bean></bean>
    public static ThreadPoolTaskExecutor getPoolTaskExecutor() {
        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
        poolTaskExecutor.setCorePoolSize(5);//核心线程数
        poolTaskExecutor.setMaxPoolSize(1000);//最大线程数
        poolTaskExecutor.setKeepAliveSeconds(30000);//空闲线程的存活时间
        poolTaskExecutor.setQueueCapacity(200);//队列最大长度
        return poolTaskExecutor;
    }

//    @Bean(name = "cloudQueryRunner")
//    @Primary
//    public CloudQueryRunner getCloudQueryRunner() {
//        return getQueryRunner("y.drore.com", control_appId, control_appSecret);
//
//    }
//
//    private static CloudQueryRunner getQueryRunner(String cloud_host, String config_appId, String control_appSecret) {
//        CloudBasicConnection cloudBasicConnection = new CloudBasicConnection(cloud_host, 80, config_appId, control_appSecret);
//        CloudPoolingConnectionManager cloudPoolingConnectionManager = new CloudPoolingConnectionManager();
//        cloudPoolingConnectionManager.setConnection(cloudBasicConnection);
//        CloudBasicDataSource cloudBasicDataSource = new CloudBasicDataSource();
//        cloudBasicDataSource.setCloudPoolingConnectionManager(cloudPoolingConnectionManager);
//        CloudQueryRunner cloudQueryRunner = new CloudQueryRunner();
//        cloudQueryRunner.setDataSource(cloudBasicDataSource);
//        return cloudQueryRunner;
//    }
}
