package com.xwbing.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.annotation.LogInfo;
import com.xwbing.domain.entity.vo.PageSysUserLoginInOutVo;
import com.xwbing.service.sys.SysUserLoginInOutService;
import com.xwbing.util.JsonResult;
import com.xwbing.util.Pagination;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

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
    @ApiOperation(value = "获取登录或登出信息", response = PageSysUserLoginInOutVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", defaultValue = "1", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页数", defaultValue = "10", paramType = "query", dataType = "int")
    })
    @GetMapping("page")
    public JSONObject page(@RequestParam(required = false) Integer inout, @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, @ApiIgnore Pagination page) {
        Pagination pagination = inOutService.page(inout, startDate, endDate, page);
        return JsonResult.toJSONObj(pagination, "获取列表成功");
    }
}
