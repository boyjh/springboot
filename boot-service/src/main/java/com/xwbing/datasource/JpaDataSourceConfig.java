package com.xwbing.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/5/31 21:38
 * 作者: xiangwb
 * 说明:
 */
@Configuration
@PropertySource("classpath:druid.properties")
public class JpaDataSourceConfig {
    @Bean(name="")
    @Primary
    @ConfigurationProperties("db1")
    public DataSource primaryDataSource() {
        return DruidDataSourceBuilder.create().build();
    }
}
