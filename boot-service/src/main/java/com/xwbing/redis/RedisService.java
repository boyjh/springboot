package com.xwbing.redis;

/**
 * 说明: 封装redis 缓存服务器服务接口
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/5 16:44
 * 作者:  xiangwb
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@PropertySource("classpath:redis.properties")
public class RedisService {
    @Autowired
    private JedisPool jedisPool;
    @Value("${redisCode}")
    private String redisCode;

    private RedisService() {

    }

    /**
     * 获取一个jedis 客户端
     *
     * @return
     */
    private Jedis getJedis() {
        return jedisPool.getResource();
    }

    /**
     * 释放Jedis资源
     *
     * @param jedis
     */
    private void returnJedies(Jedis jedis) {
        if (null != jedis) {
            jedis.close();
        }
    }
    ////////////////////////////////////////string////////////////////////////////////////

    /**
     * 添加key value
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            key = redisCode + key;
            jedis.set(key, value);
        } finally {
            returnJedies(jedis);
        }
    }

    /**
     * 添加key value 并且设置存活时间
     *
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(String key, String value, int liveTime) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            key = redisCode + key;
            jedis.setex(key, liveTime, value);
        } finally {
            returnJedies(jedis);
        }
    }

    /**
     * 获取redis value (String)
     *
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            key = redisCode + key;
            String value = jedis.get(key);
            return value;
        } finally {
            returnJedies(jedis);
        }
    }

    /**
     * 通过正则匹配keys
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.keys(pattern);
        } finally {
            returnJedies(jedis);
        }
    }
    ////////////////////////////////////////list////////////////////////////////////////

    /**
     * 添加数据到list
     *
     * @param list
     * @param values
     */
    public void lpush(String list, String... values) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            list = redisCode + list;
            jedis.lpush(list, values);
        } finally {
            returnJedies(jedis);
        }
    }

    /**
     * 获取list
     *
     * @param key
     * @return
     */
    public List<String> lrange(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            key = redisCode + key;
            return jedis.lrange(key, 0, -1);
        } finally {
            returnJedies(jedis);
        }
    }
    ////////////////////////////////////////map////////////////////////////////////////

    /**
     * 存map
     *
     * @param key
     * @param hash
     * @return
     */
    public String hmset(String key, Map<String, String> hash) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            key = redisCode + key;
            return jedis.hmset(key, hash);
        } finally {
            returnJedies(jedis);
        }
    }

    /**
     * 获取map
     *
     * @param map
     * @return
     */
    public Map<String, String> hgetAll(String map) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            map = redisCode + map;
            return jedis.hgetAll(map);
        } finally {
            returnJedies(jedis);
        }
    }

    /**
     * 添加数据到指定map
     *
     * @param map
     * @param key
     * @param value
     */

    public void hset(String map, String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            map = redisCode + map;
            jedis.hset(key, map, value);
        } finally {
            returnJedies(jedis);
        }
    }

    /**
     * 获取指定map的值
     *
     * @param map
     * @param key
     * @return
     */
    public String hget(String map, String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            map = redisCode + map;
            return jedis.hget(key, map);
        } finally {
            returnJedies(jedis);
        }
    }

    /**
     * 删除指定map的值
     *
     * @param map
     * @param key
     */
    public void hdel(String map, String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            map = redisCode + map;
            jedis.hdel(key, map);
        } finally {
            returnJedies(jedis);
        }
    }

    /**
     * 获取指定map中所有的keys
     *
     * @param map
     */
    public Set<String> hkeys(String map) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            map = redisCode + map;
            return jedis.hkeys(map);
        } finally {
            returnJedies(jedis);
        }
    }

    /**
     * 获取指定map中所有的values
     *
     * @param map
     */
    public List<String> hvals(String map) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            map = redisCode + map;
            return jedis.hvals(map);
        } finally {
            returnJedies(jedis);
        }
    }
    ///////////////////////////////////public/////////////////////////////////////////////////

    /**
     * 通过key删除
     *
     * @param key
     */
    public void del(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            key = redisCode + key;
            jedis.del(key);
        } finally {
            returnJedies(jedis);
        }
    }

    /**
     * 初始化的时候删除
     *
     * @param key
     */
    public void delInit(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.del(key);
        } finally {
            returnJedies(jedis);
        }
    }

    /**
     * 检查key是否已经存在
     *
     * @param key
     * @return
     */
    public boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            key = redisCode + key;
            return jedis.exists(key);
        } finally {
            returnJedies(jedis);
        }
    }

    /**
     * 刷新缓存
     */
    public void flushAll() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.flushAll();
        } finally {
            returnJedies(jedis);
        }
    }

    /**
     * 清空redis 所有数据
     *
     * @return
     */
    public String flushDB() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.flushDB();
        } finally {
            returnJedies(jedis);
        }
    }

    /**
     * 查看redis里有多少数据
     */
    public long dbSize() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.dbSize();
        } finally {
            returnJedies(jedis);
        }
    }

    /**
     * 检查是否连接成功
     *
     * @return
     */
    public String ping() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.ping();
        } finally {
            returnJedies(jedis);
        }
    }
}
