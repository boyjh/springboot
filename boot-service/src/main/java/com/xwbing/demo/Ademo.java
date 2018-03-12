package com.xwbing.demo;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/1/23 14:45
 * 作者: xiangwb
 * 说明: 测试用
 */
public class Ademo {
    public static void main(String[] args) {
        qq:
        for(int i=0;i<10;i++) {
            for(int j=0;j<10;j++) {
                if(j == 5) break qq;
            }
        }
        System.out.println("11"=="11");
        System.out.println("11".equalsIgnoreCase(new String("11")));
    }
}
