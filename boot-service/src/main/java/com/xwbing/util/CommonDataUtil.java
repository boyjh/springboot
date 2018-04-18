package com.xwbing.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者: xiangwb
 * 说明: 公共数据类
 */
public class CommonDataUtil {
    private static Map<String, Object> token = new HashMap<>();

    private CommonDataUtil() {
    }

    public static Object getData(String key) {
        return token.get(key);
    }

    public static void setData(String key, Object value) {
        token.put(key, value);
    }

    public static void removeData(String key) {
        token.remove(key);
    }

    public static void clearData() {
        token.clear();
    }
}
