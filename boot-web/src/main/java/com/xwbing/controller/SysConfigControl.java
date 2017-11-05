package com.xwbing.controller;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.entity.SysConfig;
import com.xwbing.service.SysConfigService;
import com.xwbing.util.JSONObjResult;
import com.xwbing.util.RestMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * 说明: 系统配置控制层
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
@Api("系统配置")
@RestController
@RequestMapping("/config/")
public class SysConfigControl {
    private static Logger log = LoggerFactory.getLogger(SysConfigControl.class);
    @Autowired
    private SysConfigService sysConfigService;

    @ApiOperation(value = "新增", notes = "新增系统配置信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "配置项的key，长度为1-50", paramType = "query", required = true, dataType = "string"),
            @ApiImplicitParam(name = "value", value = "配置项的值", paramType = "query", required = true, dataType = "string"),
            @ApiImplicitParam(name = "isEnable", value = "是否启用，格式为Y|N", paramType = "query", required = true, dataType = "string"),
            @ApiImplicitParam(name = "name", value = "配置名称，长度为1-10", paramType = "query", required = true, dataType = "string")
    })
    @PostMapping("save")
    public JSONObject save(@Valid SysConfig sysConfig) {
        String logMsg = "新增系统配置信息";
        log.info(logMsg);
        RestMessage result = sysConfigService.save(sysConfig);
        return JSONObjResult.toJSONObj(result);
    }

    @ApiOperation(value = "删除", notes = "根据code删除系统配置信息")
    @ApiImplicitParam(name = "code", value = "配置项的code", paramType = "query", required = true, dataType = "string")
    @PostMapping("removeByCode/{code}")
    public JSONObject removeByCode(@PathVariable String code) {
        String logMsg = "删除系统配置信息";
        log.info(logMsg + " code:{}", code);
        if (StringUtils.isEmpty(code)) {
            return JSONObjResult.toJSONObj("code不能为空");
        }
        RestMessage result = sysConfigService.removeByCode(code);
        return JSONObjResult.toJSONObj(result);
    }

    @ApiOperation(value = "查找", notes = "根据key查找系统配置信息")
    @ApiImplicitParam(name = "code", value = "配置项的code", paramType = "query", required = true, dataType = "string")
    @PostMapping("findByCode")
    public JSONObject findByCode(@RequestParam String code) {
        String logMsg = "根据code查找系统配置信息";
        log.info(logMsg + " code:{}", code);
        if (StringUtils.isEmpty(code)) {
            return JSONObjResult.toJSONObj("code不能为空");
        }
        SysConfig one = sysConfigService.findByCode(code);
        if (Objects.isNull(one)) {
            return JSONObjResult.toJSONObj("该对象不存在");
        }
        return JSONObjResult.toJSONObj(one, true, "");
    }
}
