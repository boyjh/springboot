package com.xwbing.service;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.BaseTest;
import com.xwbing.domain.entity.ExpressInfo;
import com.xwbing.domain.entity.sys.DataDictionary;
import com.xwbing.domain.entity.sys.SysRole;
import com.xwbing.domain.entity.vo.ExpressInfoVo;
import com.xwbing.rabbit.Sender;
import com.xwbing.redis.RedisService;
import com.xwbing.service.rest.ExpressDeliveryService;
import com.xwbing.service.rest.QRCodeZipService;
import com.xwbing.service.sys.DataDictionaryService;
import com.xwbing.service.sys.SysRoleService;
import com.xwbing.util.RSAUtil;
import com.xwbing.util.RestMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/6/1 13:20
 * 作者: xiangwb
 * 说明: 服务层功能测试
 */
@Slf4j
public class ServiceTest extends BaseTest {
    @Resource
    private RedisService redisService;
    @Resource
    private ExpressDeliveryService expressDeliveryService;
    @Resource
    private QRCodeZipService qrCodeZipService;
    @Resource
    private Sender sender;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private DataDictionaryService dictionaryService;

    @Transactional("jpaTransactionManager")
    @Test
    public void jpaTransactionTest() {
        log.info("jpa数据源事务回滚");
        DataDictionary dictionary=new DataDictionary();
        dictionary.setDescription("serviceTest");
        dictionary.setEnable("Y");
        dictionary.setCode("serviceTest");
        dictionary.setName("serviceTest");
        dictionary.setParentId("root");
        RestMessage save = dictionaryService.save(dictionary);
        Assert.assertTrue(save.isSuccess());
    }

    @Transactional
    @Test
    public void mybatisTransactionTest() {
        log.info("mybatis数据源事务回滚");
        SysRole sysRole = new SysRole();
        sysRole.setName("serviceTest");
        sysRole.setCode("serviceTest");
        sysRole.setEnable("Y");
        sysRole.setRemark("serviceTest");
        RestMessage save = sysRoleService.save(sysRole);
        Assert.assertTrue(save.isSuccess());
    }

    @Test
    public void listShipperCodeTest() {
        log.debug("获取快递列表");
        List<JSONObject> list = expressDeliveryService.listShipperCode();
        Assert.assertSame(12, list.size());
    }

    @Test
    public void expressInfoTest() {
        log.debug("快递查询");
        ExpressInfo info = new ExpressInfo();
        info.setLogisticCode("1179751819114");
        info.setShipperCode("EMS");
        if (StringUtils.isEmpty(info.getLogisticCode()) || StringUtils.isEmpty(info.getShipperCode())) {
            log.error("快递公司或物流单号不能为空");
        }
        ExpressInfoVo infoVo = expressDeliveryService.queryOrderTraces(info);
        Assert.assertTrue(infoVo.isSuccess());
    }

    @Test
    public void createQRCodeTest() {
        RestMessage qrCode = qrCodeZipService.createQRCode("test", "test");
        Assert.assertTrue(qrCode.isSuccess());
    }

    @Test
    public void decodeQRCodeTest() throws IOException {
        ClassPathResource pic = new ClassPathResource("pic");
        String path = pic.getFile().getAbsolutePath();
        File file = new File(path + File.separator + "QRCode.png");
        RestMessage decode = qrCodeZipService.decode(file);
        Assert.assertTrue(decode.isSuccess());
    }

    @Test
    public void rsaTest() {
        String en = RSAUtil.encrypt("123456");
        String de = RSAUtil.decrypt(en);
        Assert.assertEquals("123456", de);
    }

    @Test
    public void sendTest() {
        sender.sendMessage(new String[]{"mq测试"});
    }

    @Test
    public void redisTest() {
        redisService.set("redis", "xwbing");
        String s = redisService.get("redis");
        Assert.assertEquals("xwbing", s);
    }
}
