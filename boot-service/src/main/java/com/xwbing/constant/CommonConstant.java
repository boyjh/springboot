package com.xwbing.constant;

/**
 * 说明: 公共常量
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
public class CommonConstant {
    /**
     * 验证码
     */
    public static final String KEY_CAPTCHA = "captcha_code";
    /**
     * 顶级
     */
    public static final String ROOT = "root";
    /**
     * 启用
     */
    public static final String ISENABLE = "Y";
    /**
     * 禁用
     */
    public static final String ISNOTENABLE = "N";

    /**
     * 邮件服务器配置项
     */
    public static final String EMAIL_KEY = "email_config";
    /**
     * 当前登录用户
     */
    public static final String CURRENT_USER = "current_user";
    /**
     * 当前登录用户id
     */
    public static final String CURRENT_USER_ID = "current_id";
    /**
     * 权限树
     */
    public static final String AUTHORITY_THREE = "authority_three";
    /**
     * 导出报表文件名称
     */
    public static final String USER_REPORT_FILE_NAME = "人员统计报表.xls";
    /**
     * 导出报表列名称
     */
    public static final String[] USER_REPORT_COLUMNS = new String[]{" 名字", "用户名", "性别", "邮箱", "是否为管理员"};
}
