package com.xwbing.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 说明: 程序上下文配置
 * 项目名称: sbdemo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
@Configuration//相当于.xml文件中的<beans></beans>
//@ComponentScan(value = {"com.xwbing"}, excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)})
//@Import(xxx.class)//用来导入其他配置类
//@ImportResource("classpath:applicationContext.xml")//用来加载其他xml配置文件
public class ApplicationContextConfig {
    /**
     * 线程池
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
    
}
