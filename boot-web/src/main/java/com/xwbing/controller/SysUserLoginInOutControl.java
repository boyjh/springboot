package com.xwbing.controller;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.annotation.LogInfo;
import com.xwbing.entity.SysUserLoginInOut;
import com.xwbing.service.SysUserLoginInOutService;
import com.xwbing.util.JSONObjResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2017/11/7 11:16
 * 作者: xiangwb
 * 说明: 用户登录登出控制层
 */
@Api(tags = "inoutApi", description = "登陆登出相关接口")
@RestController
@RequestMapping("/inout/")
public class SysUserLoginInOutControl {
    @Resource
    private SysUserLoginInOutService inOutService;

    @LogInfo("获取登录或登出信息")
    @GetMapping("listByType")
    public JSONObject listByType(@RequestParam int inout) {
        List<SysUserLoginInOut> sysUserLoginInOuts = inOutService.listByType(inout);
        return JSONObjResult.toJSONObj(sysUserLoginInOuts, true, "获取列表成功");
    }
}
