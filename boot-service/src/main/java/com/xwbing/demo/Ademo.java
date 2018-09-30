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
        for (int i = 0; i < 57; i++) {
            list.add(i);
        }
        ArrayList<List<Integer>> result = Lists.newArrayList();
        int size = list.size();
        int rang=10;
        for (int i = 0; i < size; i += rang) {
            if (i + rang > size) {
                rang = size - i;
            }
            result.add(list.subList(i, i + rang));
        }
        result.stream().flatMap(o -> o.stream()).map(o -> o+1).forEach(integer -> System.out.println(integer));

    }
}
