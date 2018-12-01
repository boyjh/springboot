package com.xwbing.demo;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

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
        List<Integer> list = new ArrayList<>();
        IntStream.rangeClosed(1, 100).parallel().forEach(list::add);
        long count = list.stream().filter(Objects::isNull).count();
    }
}
