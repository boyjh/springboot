package com.xwbing.Rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/4/25 14:28
 * 作者: xiangwb
 * 说明: Rabbit配置类
 */
@Configuration
public class RabbitConfig {
    //声明队列
    @Bean
    public Queue serverInvokeQueue() {
        return new Queue(RabbitConstant.SERVER_INVOKE_QUEUE, true);//true表示持久化该队列
    }

    @Bean
    public Queue httpRequestQueue() {
        return new Queue(RabbitConstant.HTTP_REQUEST_QUEUE, true);
    }

    //声明交互器
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(RabbitConstant.CONTROL_EXCHANGE);
    }

    //绑定
    @Bean
    public Binding bindingExchangeMessage() {
        return BindingBuilder.bind(serverInvokeQueue()).to(topicExchange()).with(RabbitConstant.SERVER_INVOKE_ROUTING_KEY);
    }

    @Bean
    public Binding bindingExchangeMessages() {
        return BindingBuilder.bind(httpRequestQueue()).to(topicExchange()).with(RabbitConstant.HTTP_REQUEST_ROUTING_KEY);
    }
}
