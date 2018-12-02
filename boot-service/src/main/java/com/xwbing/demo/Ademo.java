package com.xwbing.demo;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.java.Log;

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
        try {
            MyThrea thread = new MyThrea();
            thread.start();
            Thread.sleep(1);
            thread.setRunning(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class MyThrea extends Thread {
    private boolean isRunning = true;

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        System.out.println("进入到run方法中了");
        while (isRunning == true) {
        }
        System.out.println("线程执行完成了");
    }
}