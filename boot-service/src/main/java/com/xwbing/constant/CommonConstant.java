package com.xwbing.constant;

/**
 * 说明: 公共常量
 * 项目名称: sbdemo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
public class CommonConstant {
	/**
	 * 顶级
	 */
	public static String ROOT = "root";
	/**
	 * 密码加密
	 */
	public static final int SALT_SIZE = 8;
	/**
	 * 密码加密
	 */
	public static int HASH_INTERATIONS = 1024;

	/**
	 * 启用
	 */
	public static String ISENABLE = "Y";
	/**
	 * 禁用
	 */
	public static String ISNOTENABLE = "N";

	/**
	 * 邮件服务器配置项
	 */
	public static final String SYSCONFIG_EMAILCONFIGKEY = "email_config";
	
	/**
	 * 服务器配置项
	 */
	public static final String SYSCONFIG_SERVER_TOPO = "server_topo";

	/**
	 * 邮箱找回密码有效时间
	 */
	public static final String SYSCONFIG_LOOK_PASSWORD_TIME = "look_password_time";
	
	/**
	 * 资源文件xml 名称
	 */
	public static final String RESOURCE_XML="resource.xml";
	/**
	 * 资源文件 sql
	 */
	public static final String RESOURCE_SQL="resource.sql";
	/**
	 * 资源详情xml文件
	 */
	public static final String RESOURCE_DETAIL_XML="resource_detail.xml";
	/**
     * 导出报表文件名称
     */
    public static String USERREPORTFILENAME = "人员统计报表.xls";
    /**
     * 导出报表列名称
     */
    public static String[] USERREPORTCOLUMNS = new String[]{"线路名称","发团人数","成人","儿童","幼儿"};
	/***
	 * 当前登录用户
	 */
	public static final String SESSION_CURRENT_USER="CURRENT_USER";

	/***
	 * 当前登录管理员用户
	 */
	public static final String SESSION_ADMIN_USER="ADMIN_USER";

}
