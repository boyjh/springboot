package com.xwbing.demo;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 项目名称: boot-module-pro
 * 创建时间: 2018/1/23 14:45
 * 作者: xiangwb
 * 说明: 测试用
 */
@Log
public class Ademo {
    private Cache<String, List<String>> dyInfosCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(24, TimeUnit.HOURS).recordStats().build();

    public static void main(String[] args) {
        ArrayList<Integer> list = Lists.newArrayList();
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }



        List<Integer> reslut = new ArrayList<>();
        list.parallelStream().forEach(reslut::add);
        System.out.println(reslut.size());

        System.out.println("");
    }
}
