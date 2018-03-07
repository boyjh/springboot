package com.xwbing.demo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xwbing.domain.entity.model.EntityModel;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/1/23 14:45
 * 作者: xiangwb
 * 说明: 测试用
 */
public class demo {
    public static void main(String[] args) {
        EntityModel jsonObject=new EntityModel();
        String s = JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullNumberAsZero);
        System.out.println(s);
    }
}
