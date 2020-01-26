package com.xwbing.controller.rest;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.annotation.LogInfo;
import com.xwbing.config.aliyun.AliYunLog;
import com.xwbing.config.redis.RedisService;
import com.xwbing.config.util.dingTalk.MarkdownMessage;
import com.xwbing.domain.entity.rest.FilesUpload;
import com.xwbing.service.rest.CookieSessionService;
import com.xwbing.service.rest.QRCodeZipService;
import com.xwbing.service.rest.UploadService;
import com.xwbing.util.EncodeUtil;
import com.xwbing.util.JsonResult;
import com.xwbing.util.RestMessage;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 说明: 测试控制层
 * 项目名称: boot-module-pro
 * 创建时间: 2017/5/5 9:21
 * 作者:  xiangwb
 */
@Slf4j
@Api(tags = "testApi", description = "测试相关接口")
@RestController
@RequestMapping("/test/")
public class TestControl {
    @Resource
    private QRCodeZipService qrCodeZipService;
    @Resource
    private CookieSessionService cookieSessionService;
    @Resource
    private UploadService uploadService;
    @Resource
    private RedisService redisService;
    @Resource
    private AliYunLog aliYunLog;

    @LogInfo("导出zip")
    @GetMapping("batchGetImage")
    public JSONObject batchGetImage(HttpServletResponse response, @RequestParam String[] names, @RequestParam String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return JsonResult.toJSONObj("zip名称不能为空");
        }
        RestMessage restMessage = qrCodeZipService.batchGetImage(response, names, fileName);
        return JsonResult.toJSONObj(restMessage);
    }

    @LogInfo("获取数据库图片")
    @GetMapping("getDbPic")
    public void getDbPic(HttpServletResponse response, @RequestParam String name, @RequestParam(required = false) String type) throws IOException {
        if (StringUtils.isNotEmpty(name)) {
            List<FilesUpload> files = uploadService.findByName(name, type);
            if (CollectionUtils.isNotEmpty(files)) {
                String data = files.get(0).getData();
                byte[] bytes = EncodeUtil.base64Decode(data);
                // 设置相应类型,告诉浏览器输出的内容为图片
                response.setContentType("image/jpeg");
                // 禁止图像缓存。
                response.setHeader("Pragma", "No-cache");
                response.setHeader("Cache-Control", "no-cache");
                response.setDateHeader("Expire", 0);
                OutputStream out = response.getOutputStream();
                out.write(bytes);
            }
        }
    }

    @LogInfo("session")
    @GetMapping("session")
    public JSONObject session(HttpServletRequest request) {
        return JsonResult.toJSONObj(cookieSessionService.session(request));
    }

    @LogInfo("cookie")
    @GetMapping("cookie")
    public JSONObject cookie(HttpServletRequest request, HttpServletResponse response) {
        return JsonResult.toJSONObj(cookieSessionService.cookie(response, request));
    }

    @LogInfo("redis")
    @GetMapping("redis")
    public JSONObject redis(@RequestParam String kv) {
        redisService.set(kv, kv);
        return JsonResult.toJSONObj(redisService.get(kv), "redis success");
    }

    @LogInfo("sendTextMessage")
    @GetMapping("sendTextMessage")
    public void sendTextMessage() {
        List<String> atMobiles = new ArrayList<>();
        atMobiles.add("134xxxx4170");
        aliYunLog.sendTextMessage("测试,请忽略", false, atMobiles, "test");
    }

    @LogInfo("sendMarkdownMessage")
    @GetMapping("sendMarkdownMessage")
    public void sendMarkdownMessage() {
        MarkdownMessage message = new MarkdownMessage();
        message.setTitle("markdown message");
        message.add(MarkdownMessage.getHeaderText(6, "六级标题"));
        message.add(MarkdownMessage.getReferenceText("引用"));
        message.add("正常字体 @134xxxx4170");
        message.add(MarkdownMessage.getBoldText("加粗字体"));
        message.add(MarkdownMessage.getItalicText("斜体"));
        ArrayList<String> orderList = new ArrayList<>();
        orderList.add("有序列表1");
        orderList.add("有序列表2");
        message.add(MarkdownMessage.getOrderListText(orderList));
        ArrayList<String> unOrderList = new ArrayList<>();
        unOrderList.add("无序列表1");
        unOrderList.add("无序列表2");
        message.add(MarkdownMessage.getUnOrderListText(unOrderList));
        message.add(MarkdownMessage.getImageText("http://img01.taobaocdn.com/top/i1/LB1GCdYQXXXXXXtaFXXXXXXXXXX"));
        message.add(MarkdownMessage.getLinkText("百度", "http://baidu.com"));
        message.setAtAll(true);
        List<String> atMobiles = new ArrayList<>();
        atMobiles.add("134xxxx4170");
        message.setAtMobiles(atMobiles);
        aliYunLog.sendMarkdownMessage(message);
    }
}

