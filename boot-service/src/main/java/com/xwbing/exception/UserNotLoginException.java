/*
 * Copyright (C) 2016 Zhejiang DRORE Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.drore.com.
 * Developer Web Site: http://open.drore.com.
 */
package com.xwbing.exception;

/**
 * 说明: 用户系统中未登陆异常
 * 项目名称: sbdemo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
public class UserNotLoginException extends RuntimeException {
    private static final long serialVersionUID = -3173607918780955199L;


    public UserNotLoginException() {
        // TODO Auto-generated constructor stub
    }

    public UserNotLoginException(String msg) {
        super(msg);
    }


}
