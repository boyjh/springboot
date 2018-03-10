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
public class Ademo {
    public static void main(String[] args) {
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            integers.add(i);
        }
        long l = System.currentTimeMillis();
        List<Integer> collect = integers.stream().filter(integer ->integer==2 ).collect(Collectors.toList());
        long l1 = System.currentTimeMillis();
        System.out.println(l1-l);
        System.out.println(collect);
        long l3 = System.currentTimeMillis();
        List<Integer> collect1 = integers.parallelStream().filter(integer ->integer==2 ).collect(Collectors.toList());
        long l4 = System.currentTimeMillis();
        System.out.println(l4-l3);
        System.out.println(collect1);

    }
}
