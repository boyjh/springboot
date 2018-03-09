package com.xwbing.demo;

import com.xwbing.domain.entity.sys.SysUser;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/1/23 14:45
 * 作者: xiangwb
 * 说明: 测试用
 */
public class demo {
    public static void main(String[] args) {
        SysUser sysUser=new SysUser();
        Map<String, Object> stringObjectHashMap = new HashMap<>();
        Object o = stringObjectHashMap.get(sysUser.getAdmin());
        System.out.println("");


    }
}
