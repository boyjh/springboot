package com.xwbing.Rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.UUID;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/4/25 14:48
 * 作者: xiangwb
 * 说明:
 */
@Component
public class Sender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    @Resource
    private RabbitTemplate rabbitTemplate;
    private final Logger logger = LoggerFactory.getLogger(Sender.class);

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String cause) {
        if (b) {
            logger.info("消息发送成功:{}", correlationData);
        } else {
            logger.info("消息发送失败:{}", cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.info(message.getMessageProperties().getCorrelationIdString() + "发送失败");
    }

    //发送消息，不需要实现任何接口，供外部调用。
    public void send(String msg) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        logger.info("开始发送消息:{}", msg.toLowerCase());
        String response = rabbitTemplate.convertSendAndReceive(RabbitConstant.CONTROL_EXCHANGE, RabbitConstant.SERVER_INVOKE_ROUTINGKEY, msg, correlationId).toString();
        logger.info("结束发送消息:{}", msg.toLowerCase());
        logger.info("消费者响应:{}消息处理完成", response);
    }
}
