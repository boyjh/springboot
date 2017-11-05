package com.xwbing.controller;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.service.MailService;
import com.xwbing.util.JSONObjResult;
import com.xwbing.util.RestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目名称: sb-boot-module-demo
 * 创建时间: 2017/9/18 13:42
 * 作者: xiangwb
 * 说明:邮件测试控制层
 */
@RestController
@RequestMapping("/mail/")
public class MailTestControl {
    @Autowired
    private MailService mailService;

    /**
     * 发送纯文本邮件
     *
     * @return
     */
    @GetMapping("sendSimpleMail")
    public JSONObject sendSimpleMail() {
        RestMessage restMessage = mailService.sendSimpleMail("786461501@qq.com", "测试邮件", "收到一个纯文本邮件");
        return JSONObjResult.toJSONObj(restMessage);
    }

    /**
     * 发送html格式邮件
     *
     * @return
     */
    @GetMapping("sendHtmlMail")
    public JSONObject sendHtmlMail() {
        String content = "<html>" +
                "<body>" +
                "<h3>hello world ! 这是一封Html邮件!</h3>" +
                "</body>" +
                "</html>";
        RestMessage restMessage = mailService.sendHtmlMail("786461501@qq.com", "html测试邮件", content);
        return JSONObjResult.toJSONObj(restMessage);
    }

    /**
     * 发送带附件邮件
     *
     * @return
     */
    @GetMapping("sendAttachmentsMail")
    public JSONObject sendAttachmentsMail() {
        String path = "C:\\Users\\admin\\Desktop\\qq.txt";
        RestMessage restMessage = mailService.sendAttachmentsMail("786461501@qq.com", "附件测试邮件", "收到一个带附件邮件", path);
        return JSONObjResult.toJSONObj(restMessage);
    }

    /**
     * 发送文本内嵌图片邮件
     *
     * @return
     */
    @GetMapping("sendInlineResourceMail")
    public JSONObject sendInlineResourceMail() {
        String rscId = "pic";
        String content = "<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\'></body></html>";
        String imgPath = "C:\\Users\\admin\\Desktop\\0000001.png";
        RestMessage restMessage = mailService.sendInlineResourceMail("786461501@qq.com", "内嵌图片测试邮件", content, imgPath, rscId);
        return JSONObjResult.toJSONObj(restMessage);
    }
}
