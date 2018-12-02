package com.xwbing.demo;

import lombok.extern.java.Log;

/**
 * 项目名称: boot-module-pro
 * 创建时间: 2018/1/23 14:45
 * 作者: xiangwb
 * 说明: 测试用
 */
@Log
public class Ademo {
    public static void main(String[] args) throws InterruptedException {
        MyThrea thread = new MyThrea();
        thread.start();
        Thread.sleep(5);
        thread.setRunning(false);
        System.out.println("main end " + thread.isRunning());

    }
}

class MyThrea extends Thread {
    private  boolean isRunning = true;

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        System.out.println("进入到run方法中了");
        while (isRunning) {
        }
        System.out.println("线程执行完成了");
    }


}
