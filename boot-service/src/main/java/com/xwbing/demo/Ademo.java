package com.xwbing.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/1/23 14:45
 * 作者: xiangwb
 * 说明: 测试用
 */
public class Ademo {
    public static void main(String[] args) {
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            integers.add(i);
        }
//        long l = System.currentTimeMillis();
//        integers.forEach(integer -> {
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        long l1 = System.currentTimeMillis();
//        System.out.println(l1-l);

        long l3 = System.currentTimeMillis();
        integers.parallelStream().forEach(integer -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long l4 = System.currentTimeMillis();
        System.out.println(l4-l3);



    }
}
