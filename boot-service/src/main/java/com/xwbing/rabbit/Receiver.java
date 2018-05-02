package com.xwbing.rabbit;

import com.xwbing.service.rest.MailService;
import com.xwbing.util.RestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;

import static com.xwbing.rabbit.RabbitConstant.EMAIL_QUEUE;
import static com.xwbing.rabbit.RabbitConstant.HTTP_REQUEST_QUEUE;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/4/25 15:12
 * 作者: xiangwb
 * 说明: 消费者
 */
@Component
public class Receiver {
    @Resource
    private MailService mailService;
    private final Logger logger = LoggerFactory.getLogger(Receiver.class);

    /**
     * 处理邮件队列信息
     *
     * @param msg
     * @return
     */
    @RabbitListener(queues = EMAIL_QUEUE)
    public String processEmail(String[] msg) {
        String format = MessageFormat.format("你的用户名是:{0},密码是:{1}", msg[1], msg[2]);
        RestMessage restMessage = mailService.sendSimpleMail(msg[0], "注册成功", format);
        boolean success = restMessage.isSuccess();
        return MessageFormat.format("成功发送邮件给{0}:{1}", msg[1], success);
    }

    /**
     * 处理http队列消息
     *
     * @param msg
     */
    @RabbitListener(queues = HTTP_REQUEST_QUEUE)
    public String processHttp(String[] msg) {
        String response = MessageFormat.format("收到{0}队列的消息:{1}", HTTP_REQUEST_QUEUE, msg);
        return response.toUpperCase();
    }
}
