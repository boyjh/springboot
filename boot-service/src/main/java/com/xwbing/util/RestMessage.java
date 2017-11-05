package com.xwbing.util;

import lombok.Data;

import java.io.Serializable;
/**
 * 说明:返回消息封装类
 * 创建日期: 2017年3月3日 上午11:43:13
 * 作者: xiangwb
 */
@Data
public class RestMessage implements Serializable {
    private static final long serialVersionUID = -4167591341943919542L;
    private boolean success = false;// 默认false
    private String message;//  成功、错误返回提示信息
    private Object data;// 返回的数据
    private String id;// 新增、修改主鍵返回id
    private Integer code;//错误码
}
