package com.xwbing.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/1/23 14:45
 * 作者: xiangwb
 * 说明: 测试用
 */
public class demo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String s="001";
        Integer integer = Integer.valueOf(s);
        System.out.println(integer);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
    }
}
