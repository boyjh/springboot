package com.xwbing.controller;

import com.xwbing.annotation.LogInfo;
import com.xwbing.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 说明:
 * 项目名称: sbdemo
 * 创建时间: 2017/5/5 9:21
 * 作者:  xiangwb
 */
@RestController
@RequestMapping("/test/")
public class TestControl {
    @Autowired
    private RedisService redisService;
    private final Logger logger = LoggerFactory.getLogger(TestControl.class);

    @LogInfo("redisLog")
    @GetMapping("redis")
    public void redis() {
        redisService.set("redis", "xwbing");
        String s = redisService.get("redis");
        System.out.println(s);
        logger.info("获取的数据为:" + s);
    }

    @LogInfo("log")
    @GetMapping("log")
    public void log() {
        logger.info("info");
        logger.error("error");
    }
}
