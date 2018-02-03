package com.xwbing.demo;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/1/23 14:45
 * 作者: xiangwb
 * 说明: 测试用
 */
public class demo {
    public static void main(String[] args) {

        ArrayList<Integer> objects = new ArrayList<>();
        long l2 = System.currentTimeMillis();
        for (int i=0;i<1000000;i++){
            objects.add(i);
        }
        long l3 = System.currentTimeMillis();
        System.out.println(l3-l2);

        LinkedList<Integer> list = new LinkedList<>();
        long l = System.currentTimeMillis();
        for (int i=0;i<1000000;i++){
            list.add(i);
        }
        long l1 = System.currentTimeMillis();
        System.out.println(l1-l);



    }
}
