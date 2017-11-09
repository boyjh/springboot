package com.xwbing.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Set;

/**
 * 说明: 启动时刷新redis缓存
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/5 16:44
 * 作者:  xiangwb
 */
@Component
@PropertySource("classpath:redis.properties")//只能读取properties文件
public class RedisStartupInit implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(RedisStartupInit.class);
    @Resource
    private RedisService redisService;
    @Value("${redisCode}")
    private String redisCode;

    public void afterPropertiesSet() throws Exception {
        logger.info("redis has been start");
        System.out.println("---------启动执行方法---刷新缓存----------------");
        redisService.set("xwb", "测试数据");
        System.out.printf(redisService.get("xwb"));
        redisService.del("xwb");
        System.out.printf(String.valueOf(redisService.exists("xwb")));
        Set<String> set = redisService.keys(redisCode + "*");
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String keyStr = it.next();
            System.out.println("启动删除缓存名称==" + keyStr);
            redisService.delInit(keyStr);
        }
    }
}
