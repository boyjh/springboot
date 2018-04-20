package com.xwbing.demo;

import com.alibaba.fastjson.JSONArray;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/1/23 14:45
 * 作者: xiangwb
 * 说明: 测试用
 */
public class Ademo {
    public static void main(String[] args) {
        Map<String, String> aa = new HashMap<>();
        aa.put(null, "aa");
        String s = aa.get(null);
        System.out.println(s);
        JSONArray jsonArray = JSONArray.parseArray("[1,2]");
        System.out.println("");
        ConcurrentHashMap
    }

}
