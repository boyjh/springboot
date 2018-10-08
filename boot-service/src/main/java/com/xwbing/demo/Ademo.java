package com.xwbing.demo;

import com.google.common.collect.Lists;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称: boot-module-pro
 * 创建时间: 2018/1/23 14:45
 * 作者: xiangwb
 * 说明: 测试用
 */
@Log
public class Ademo {
    public static void main(String[] args) {




        ArrayList<Integer> list = Lists.newArrayList();
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }
        List<Integer> integers = list.subList(0, 10);
        integers.add(8);
        System.out.println("");

//        List<Integer> result =new ArrayList<>();
//        list.parallelStream().forEach(integer -> {
//            result.add(integer);
//        });
//        System.out.println(result.size());
//        for (int i = 0; i < result.size(); i++) {
//            System.out.println(result.get(i));
//        }
//        System.out.println("");
//        Map<String,Object> ma = new HashMap<>();

    }
}
