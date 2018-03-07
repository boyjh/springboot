package com.xwbing.demo;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/1/23 14:45
 * 作者: xiangwb
 * 说明: 测试用
 */
public class demo {
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("a","aa");
        map.put("aaa","");
        map.put("bbb",null);
        System.out.println(map);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("a","aa");
        jsonObject.put("aaa","");
        jsonObject.put("bbb",null);
        System.out.println(jsonObject);

    }
}
