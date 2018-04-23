package com.xwbing.configuration;

import com.xwbing.util.CommonDataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 说明: 定时任务定时类
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
@Configuration
@EnableScheduling // 启用定时任务
public class SchedulingConfig {
    private final Logger logger = LoggerFactory.getLogger(SchedulingConfig.class);

    @Scheduled(cron = "0 0 6 * * ? ")//每天6点开启定时任务
    public void scheduler() {
        logger.info("清除公共数据类过期数据===================");
        CommonDataUtil.clearExpiryData();
    }
}
