package com.xwbing.demo;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/1/23 14:45
 * 作者: xiangwb
 * 说明: 测试用
 */
public class demo {
    public static void main(String[] args) {
        List<JSONObject> list = new ArrayList<>();
        for (int i = 0; i <2 ; i++) {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("a","a");
            jsonObject.put("b","b");
            jsonObject.put("c","c");
            list.add(jsonObject);
        }
        List<String> a1 = list.stream().map(jsonObject ->
             jsonObject.getString("a")
        ).collect(Collectors.toList());
        System.out.println(a1);


    }
}
