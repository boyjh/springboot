package com.xwbing.controller;

import com.xwbing.annotation.LogInfo;
import com.xwbing.redis.RedisService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 说明: 测试控制层
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/5 9:21
 * 作者:  xiangwb
 */
@Api(tags = "testApi", description = "测试相关接口")
@RestController
@RequestMapping("/test/")
public class TestControl {
    @Resource
    private RedisService redisService;
    private final Logger logger = LoggerFactory.getLogger(TestControl.class);

    @LogInfo("redis功能测试")
    @GetMapping("redis")
    public void redis() {
        redisService.set("redis", "xwbing");
        String s = redisService.get("redis");
        logger.info("redis获取的数据为:" + s);
    }

    @LogInfo("log功能测试")
    @GetMapping("log")
    public void log() {
        logger.info("info test");
        logger.error("error test");
    }
}
