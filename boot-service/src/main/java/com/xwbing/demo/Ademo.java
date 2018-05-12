package com.xwbing.demo;

import org.apache.commons.lang3.StringUtils;

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
        List<String> list=new ArrayList<>();
        List<String> collect = list.stream().filter(s -> StringUtils.isNotEmpty(s)).collect(Collectors.toList());
        String[] s={"a","b"};
    }
}
