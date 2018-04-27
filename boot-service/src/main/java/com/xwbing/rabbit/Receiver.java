package com.xwbing.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

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
     * 处理server队列信息
     *
     * @param msg
     * @return
     */
    @RabbitListener(queues = SERVER_INVOKE_QUEUE)
    public String processServer(String msg) {
        String response = MessageFormat.format("收到{0}队列的消息:{1}", SERVER_INVOKE_QUEUE, msg);
        return response.toUpperCase();
    }

    /**
     * 处理http队列消息
     *
     * @param msg
     */
    @RabbitListener(queues = HTTP_REQUEST_QUEUE)
    public String processHttp(String msg) {
        String response = MessageFormat.format("收到{0}队列的消息:{1}", HTTP_REQUEST_QUEUE, msg);
        return response.toUpperCase();
    }
}
