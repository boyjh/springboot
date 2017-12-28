package com.xwbing.controller;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.annotation.LogInfo;
import com.xwbing.domain.entity.ExpressInfo;
import com.xwbing.domain.entity.vo.ExpressInfoVo;
import com.xwbing.redis.RedisService;
import com.xwbing.service.other.CookieSessionService;
import com.xwbing.service.other.ExpressDeliveryService;
import com.xwbing.service.other.QRCodeZipService;
import com.xwbing.util.HttpClientUtil;
import com.xwbing.util.JSONObjResult;
import com.xwbing.util.RSAUtil;
import com.xwbing.util.RestMessage;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * 说明: 测试控制层
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/5 9:21
 * 作者:  xiangwb
 */
@Api(tags = "testApi", description = "测试相关接口")
@RestController
@RequestMapping("/test/")
public class TestControl {
    @Resource
    private RedisService redisService;
    @Resource
    private ExpressDeliveryService expressDeliveryService;
    @Resource
    private QRCodeZipService qrCodeZipService;
    @Resource
    private CookieSessionService cookieSessionService;
    private final Logger logger = LoggerFactory.getLogger(TestControl.class);

    @LogInfo("redis功能测试")
    @GetMapping("redis")
    public void redis() {
        redisService.set("redis", "xwbing");
        String s = redisService.get("redis");
        logger.info("redis获取的数据为:" + s);
    }

    @LogInfo("log功能测试")
    @GetMapping("log")
    public void log() {
        logger.info("info test");
        logger.error("error test");
    }

    @LogInfo("获取快递列表")
    @GetMapping("listShipperCode")
    public JSONObject listShipperCode() {
        List<JSONObject> list = expressDeliveryService.listShipperCode();
        return JSONObjResult.toJSONObj(list, "");
    }

    @LogInfo("快递查询")
    @PostMapping("expressInfo")
    public JSONObject getExpressInfo(@RequestBody ExpressInfo info) {
        if (StringUtils.isEmpty(info.getLogisticCode()) || StringUtils.isEmpty(info.getShipperCode()))
            return JSONObjResult.toJSONObj("快递公司或物流单号不能为空");
        ExpressInfoVo infoVo = expressDeliveryService.queryOrderTraces(info);
        return JSONObjResult.toJSONObj(infoVo, "查询快递信息成功");
    }

    @LogInfo("生成二维码")
    @PostMapping("createQRCode")
    public JSONObject createQRCode(@RequestParam String name, @RequestParam String text) {
        RestMessage qrCode = qrCodeZipService.createQRCode(name, text);
        return JSONObjResult.toJSONObj(qrCode);
    }

    @LogInfo("解析二维码")
    @GetMapping("decode")
    public JSONObject decode(@RequestParam String path) {
        if (StringUtils.isEmpty(path))
            return JSONObjResult.toJSONObj("二维码图片路径不能为空");
        File file = new File(path);
        RestMessage decode = qrCodeZipService.decode(file);
        return JSONObjResult.toJSONObj(decode);
    }

    @LogInfo("导出zip")
    @GetMapping("batchGetImage")
    public JSONObject batchGetImage(HttpServletResponse response, @RequestParam String[] names, @RequestParam String fileName) {
        if (StringUtils.isEmpty(fileName))
            return JSONObjResult.toJSONObj("zip名称不能为空");
        RestMessage restMessage = qrCodeZipService.batchGetImage(response, names, fileName);
        return JSONObjResult.toJSONObj(restMessage);
    }

    @LogInfo("rsa")
    @GetMapping("rsa")
    public void rsa() {
        String en = RSAUtil.encrypt("123456");
        String de = RSAUtil.decrypt(en);
        System.out.println(de);
    }

    @LogInfo("httpUtil")
    @PostMapping("httpUtil")
    public JSONObject httpUtil(@RequestBody JSONObject param) {
//        String url = "http://localhost:8080/user/listAll";
//        return HttpClientUtil.get(url);
        String url = "http://localhost:8080/user/save";
        return HttpClientUtil.postByJson(url, param);
    }

    @LogInfo("session")
    @GetMapping("session")
    public JSONObject session(HttpServletRequest request) {
        return JSONObjResult.toJSONObj(cookieSessionService.session(request));
    }

    @LogInfo("cookie")
    @GetMapping("cookie")
    public JSONObject cookie(HttpServletRequest request, HttpServletResponse response) {
        return JSONObjResult.toJSONObj(cookieSessionService.cookie(response, request));
    }
}
