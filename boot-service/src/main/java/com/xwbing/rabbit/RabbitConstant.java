package com.xwbing.rabbit;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/4/25 14:34
 * 作者: xiangwb
 * 说明: 常量
 */
public class RabbitConstant {
    /**
     * 服务器调用
     */
    public static final String SERVER_INVOKE_QUEUE = "server.invoke";
    /**
     * http请求
     */
    public static final String HTTP_REQUEST_QUEUE = "http.request";
    /**
     * 服务器调用路由键（*表示一个词,#表示零个或多个词）
     */
    public static final String SERVER_INVOKE_ROUTING_KEY = "server.invoke.key";
    /**
     * http请求路由键
     */
    public static final String HTTP_REQUEST_ROUTING_KEY = "http.request.key";
    /**
     * 交换机
     */
    public static final String CONTROL_EXCHANGE = "control.exchange";
}
