package com.xwbing.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.xwbing.domain.entity.model.DruidDataSourceModel;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * 说明: 德鲁伊监控:druid/index.html
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
@Configuration
@EnableConfigurationProperties(DruidDataSourceModel.class)
public class DruidConfig {
    @Resource
    private DruidDataSourceModel druidDataSourceModel;

    @Bean
    @Primary
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(druidDataSourceModel.getUrl());
        dataSource.setDriverClassName(druidDataSourceModel.getDriver());
        dataSource.setUsername(druidDataSourceModel.getUsername());
        dataSource.setPassword(druidDataSourceModel.getPassword());
        dataSource.setInitialSize(druidDataSourceModel.getInitSize());
        dataSource.setMinIdle(druidDataSourceModel.getMinIdle());
        dataSource.setMaxActive(druidDataSourceModel.getMaxActive());
        dataSource.setMaxWait(druidDataSourceModel.getMaxWait());
        dataSource.setTimeBetweenEvictionRunsMillis(druidDataSourceModel.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(druidDataSourceModel.getMinEvictableIdleTimeMillis());
        dataSource.setValidationQuery(druidDataSourceModel.getValidationQuery());
        dataSource.setTestWhileIdle(druidDataSourceModel.isTestWhileIdle());
        dataSource.setTestOnBorrow(druidDataSourceModel.isTestOnBorrow());
        dataSource.setTestOnReturn(druidDataSourceModel.isTestOnReturn());
        dataSource.setPoolPreparedStatements(druidDataSourceModel.isPoolPreparedStatements());
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(druidDataSourceModel.getMaxPoolPreparedStatementPerConnectionSize());
        dataSource.setConnectionProperties(druidDataSourceModel.getConnectionProperties());
        dataSource.setUseGlobalDataSourceStat(druidDataSourceModel.isUseGlobalDataSourceStat());
        try {
            dataSource.setFilters(druidDataSourceModel.getFilters());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }
}
