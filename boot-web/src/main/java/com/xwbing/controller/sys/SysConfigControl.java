package com.xwbing.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.annotation.LogInfo;
import com.xwbing.domain.entity.sys.SysConfig;
import com.xwbing.domain.entity.vo.ListSysConfigVo;
import com.xwbing.domain.entity.vo.RestMessageVo;
import com.xwbing.domain.entity.vo.SysConfigVo;
import com.xwbing.service.sys.SysConfigService;
import com.xwbing.util.JsonResult;
import com.xwbing.util.RestMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 说明: 系统配置控制层
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
@Api(tags = "configApi", description = "系统配置相关接口")
@RestController
@RequestMapping("/config/")
public class SysConfigControl {
    private final Logger logger = LoggerFactory.getLogger(SysConfigControl.class);
    @Resource
    private SysConfigService sysConfigService;

    @LogInfo("新增系统配置信息")
    @ApiOperation(value = "新增", notes = "新增系统配置信息", response = RestMessageVo.class)
    @PostMapping("save")
    public JSONObject save(@RequestBody @Valid SysConfig sysConfig) {
        String logMsg = "新增系统配置信息";
        logger.info(logMsg);
        RestMessage result = sysConfigService.save(sysConfig);
        return JsonResult.toJSONObj(result);
    }

    @LogInfo("根据code删除系统配置信息")
    @ApiOperation(value = "删除", notes = "根据code删除系统配置信息", response = RestMessageVo.class)
    @ApiImplicitParam(name = "code", value = "配置项的code", paramType = "query", required = true, dataType = "string")
    @GetMapping("removeByCode")
    public JSONObject removeByCode(@RequestParam String code) {
        String logMsg = "删除系统配置信息";
        logger.info(logMsg + " code:{}", code);
        if (StringUtils.isEmpty(code)) {
            return JsonResult.toJSONObj("code不能为空");
        }
        RestMessage result = sysConfigService.removeByCode(code);
        return JsonResult.toJSONObj(result);
    }

    @LogInfo("修改系统配置信息")
    @ApiOperation(value = "修改", notes = "修改系统配置信息", response = RestMessageVo.class)
    @PostMapping("update")
    public JSONObject update(@RequestBody @Valid SysConfig sysConfig) {
        String logMsg = "修改系统配置信息";
        logger.info(logMsg);
        if (StringUtils.isEmpty(sysConfig.getCode())) {
            return JsonResult.toJSONObj("配置项的code不能为空");
        }
        RestMessage result = sysConfigService.update(sysConfig);
        return JsonResult.toJSONObj(result);
    }

    @LogInfo("根据key查找系统配置信息")
    @ApiOperation(value = "查找", notes = "根据key查找系统配置信息", response = SysConfigVo.class)
    @ApiImplicitParam(name = "code", value = "配置项的code", paramType = "query", required = true, dataType = "string")
    @GetMapping("getByCode")
    public JSONObject getByCode(@RequestParam String code) {
        String logMsg = "根据code查找系统配置信息";
        logger.info(logMsg + " code:{}", code);
        if (StringUtils.isEmpty(code)) {
            return JsonResult.toJSONObj("code不能为空");
        }
        SysConfig one = sysConfigService.getByCode(code);
        if (one == null) {
            return JsonResult.toJSONObj("该配置不存在");
        }
        return JsonResult.toJSONObj(one, "");
    }

    @LogInfo("根据是否启用查找配置列表")
    @ApiOperation(value = "查找列表", notes = "根据是否启用查找配置列表", response = ListSysConfigVo.class)
    @ApiImplicitParam(name = "enable", value = "是否启用，格式为Y|N", paramType = "query", required = true, dataType = "string")
    @GetMapping("listByEnable")
    public JSONObject listByEnable(@RequestParam String enable) {
        String logMsg = "根据是否启用查找配置列表";
        logger.info(logMsg + " enable:{}", enable);
        if (StringUtils.isEmpty(enable)) {
            return JsonResult.toJSONObj("是否启用不能为空");
        }
        List<SysConfig> byEnable = sysConfigService.listByEnable(enable);
        return JsonResult.toJSONObj(byEnable, "");
    }
}
