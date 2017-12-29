package com.xwbing.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 作者: xiangwb
 * 说明:
 */
public class JsonConverUtil {
    public static JSONArray str2JsonArray(String string) {
        return JSON.parseArray(string);
    }

    public static JSONArray obj2JsonArray(Object object) {
        return JSON.parseArray(JSONObject.toJSONString(object));
    }

    public static JSONObject str2JsonObj(String string) {
        return JSON.parseObject(string);
    }

    public static JSONObject obj2JsonObj(Object object) {
        return JSON.parseObject(JSONObject.toJSONString(object));
    }
}
