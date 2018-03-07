package com.xwbing.demo;

import com.alibaba.fastjson.JSONObject;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/1/23 14:45
 * 作者: xiangwb
 * 说明: 测试用
 */
public class demo {
    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aa", null);
        System.out.println(jsonObject);
    }
}
