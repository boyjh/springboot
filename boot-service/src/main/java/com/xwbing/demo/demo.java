package com.xwbing.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/1/23 14:45
 * 作者: xiangwb
 * 说明: 测试用
 */
public class demo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        List<Integer> finalList = new ArrayList<>();
        int size = list.size();
        CompletableFuture[] futures = new CompletableFuture[size];
        for (int i = 0; i < size; i++) {
            Integer integer = list.get(i);
            futures[i] = CompletableFuture.supplyAsync(() -> finalList.add(integer));
        }
        CompletableFuture.allOf(futures).join();
        System.out.println(finalList);
    }
}
