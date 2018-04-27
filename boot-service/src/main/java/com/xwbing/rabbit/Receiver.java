package com.xwbing.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.xwbing.rabbit.RabbitConstant.HTTP_REQUEST_QUEUE;
import static com.xwbing.rabbit.RabbitConstant.SERVER_INVOKE_QUEUE;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/4/25 15:12
 * 作者: xiangwb
 * 说明: 消费者
 */
@Component
public class Receiver {
    private final Logger logger = LoggerFactory.getLogger(Receiver.class);

    /**
     * 处理消息并响应消息
     *
     * @param msg
     * @return
     */
    @RabbitListener(queues = SERVER_INVOKE_QUEUE)
    public String processServer(String msg) {
        logger.info("接收到来自{}队列的消息:{}", SERVER_INVOKE_QUEUE, msg);
        return msg.toUpperCase();
    }

    /**
     * 处理消息
     *
     * @param msg
     */
    @RabbitListener(queues = HTTP_REQUEST_QUEUE)
    public void processHttp(String msg) {
        logger.info("接收到来自{}队列的消息:{}", HTTP_REQUEST_QUEUE, msg);
    }
}
