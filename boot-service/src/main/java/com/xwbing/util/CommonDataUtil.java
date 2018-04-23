package com.xwbing.util;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 作者: xiangwb
 * 说明: 公共数据类
 */
public class CommonDataUtil {
    private static Map<String, Object> token = new HashMap<>();
    private static final long MINUTE = 1000 * 60;

    private CommonDataUtil() {
    }

    /**
     * 存数据
     *
     * @param key
     * @param value
     */
    public static void setData(String key, Object value) {
        JSONObject object = new JSONObject();
        object.put("value", value);
        object.put("expiry", -1);
        token.put(key, object);
    }

    /**
     * 存数据,带有效期
     *
     * @param key    key
     * @param value  value
     * @param minute 分钟
     */
    public static void setData(String key, Object value, int minute) {
        JSONObject object = new JSONObject();
        long currentTimeMillis = System.currentTimeMillis();
        object.put("value", value);
        object.put("expiry", currentTimeMillis + minute * MINUTE);
        token.put(key, object);
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public static Object getData(String key) {
        JSONObject object = (JSONObject) token.get(key);
        if (object != null) {
            Object value = object.get("value");
            long expiry = object.getLongValue("expiry");
            if (-1 == expiry) {
                return value;
            } else {
                long currentTimeMillis = System.currentTimeMillis();
                if (expiry >= currentTimeMillis) {
                    return value;
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }

    }

    /**
     * 删除某条数据
     *
     * @param key
     */
    public static void removeData(String key) {
        token.remove(key);
    }

    /**
     * 删除过期数据
     */
    public static void clearExpiryData() {
        long currentTimeMillis = System.currentTimeMillis();
        Set<Map.Entry<String, Object>> entries = token.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            JSONObject object = (JSONObject) next.getValue();
            if (object != null) {
                long expiry = object.getLongValue("expiry");
                if (-1 != expiry) {
                    if (expiry < currentTimeMillis) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    /**
     * 清空所有数据
     */
    public static void clearAllData() {
        token.clear();
    }

    public static void main(String[] args) {
        CommonDataUtil.setData("a", "a", 1);
        CommonDataUtil.setData("b", "b");
        Object a = CommonDataUtil.getData("a");
        Object b = CommonDataUtil.getData("b");
        CommonDataUtil.clearExpiryData();
    }
}
