package com.xwbing.Rabbit;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/4/25 14:34
 * 作者: xiangwb
 * 说明:
 */
public class RabbitConstant {
    /**
     * 服务器调用
     */
    public static final String SERVER_INVOKE_QUEUE = "server_invoke";
    /**
     * http请求
     */
    public static final String HTTP_REQUEST_QUEUE = "http_request";
    /**
     * 服务器调用路由键
     */
    public static final String SERVER_INVOKE_ROUTING_KEY = "server_invoke.key";
    /**
     * http请求路由键
     */
    public static final String HTTP_REQUEST_ROUTING_KEY = "http_request.key";
    /**
     * 交换器
     */
    public static final String CONTROL_EXCHANGE = "control_exchange";
}
