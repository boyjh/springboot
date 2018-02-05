package com.xwbing.demo;

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
        long l = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            list.add(i);
        }
        list = list.stream().map(integer -> integer * 10).collect(Collectors.toList());
        long l1 = System.currentTimeMillis();
        System.out.println(l1 - l);
    }
}
