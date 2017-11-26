package com.xwbing.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.annotation.LogInfo;
import com.xwbing.domain.entity.sys.SysAuthority;
import com.xwbing.domain.entity.sys.SysRole;
import com.xwbing.domain.entity.sys.SysRoleAuthority;
import com.xwbing.service.sys.SysAuthorityService;
import com.xwbing.service.sys.SysRoleAuthorityService;
import com.xwbing.service.sys.SysRoleService;
import com.xwbing.util.JSONObjResult;
import com.xwbing.util.RestMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2017/11/14 10:41
 * 作者: xiangwb
 * 说明: 角色控制层
 */
@Api(tags = "sysRoleApi", description = "角色相关接口")
@RestController
@RequestMapping("/role/")
public class SysRoleControl {
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysAuthorityService sysAuthorityService;
    @Resource
    private SysRoleAuthorityService sysRoleAuthorityService;

    @LogInfo("添加角色")
    @ApiOperation(value = "添加角色")
    @PostMapping("save")
    public JSONObject save(@RequestBody SysRole sysRole) {
        RestMessage result = sysRoleService.save(sysRole);
        return JSONObjResult.toJSONObj(result);
    }

    @LogInfo("删除角色")
    @ApiOperation(value = "删除角色")
    @GetMapping("removeById")
    public JSONObject removeById(@RequestParam String id) {
        if (StringUtils.isEmpty(id))
            return JSONObjResult.toJSONObj("主键不能为空");
        RestMessage result = sysRoleService.removeById(id);
        return JSONObjResult.toJSONObj(result);
    }

    @LogInfo("修改角色")
    @ApiOperation(value = "修改角色")
    @PostMapping("update")
    public JSONObject update(@RequestBody SysRole sysRole) {
        if (StringUtils.isEmpty(sysRole.getId()))
            return JSONObjResult.toJSONObj("主键不能为空");
        RestMessage result = sysRoleService.update(sysRole);
        return JSONObjResult.toJSONObj(result);
    }

    @LogInfo("获取角色详情")
    @ApiOperation(value = "获取角色详情")
    @GetMapping("getById")
    public JSONObject getById(@RequestParam String id) {
        if (StringUtils.isEmpty(id))
            return JSONObjResult.toJSONObj("主键不能为空");
        SysRole sysRole = sysRoleService.getById(id);
        if (sysRole == null)
            return JSONObjResult.toJSONObj("该角色不存在");
        return JSONObjResult.toJSONObj(sysRole, true, "");
    }

    @LogInfo("根据是否启用查询所有角色")
    @ApiOperation(value = "根据是否启用查询所有角色")
    @ApiImplicitParam(name = "enable", value = "是否启用,格式Y|N", paramType = "query", dataType = "string")
    @GetMapping("listByEnable")
    public JSONObject listByEnable(String enable) {
        List<SysRole> sysRoles = sysRoleService.listAllByEnable(enable);
        return JSONObjResult.toJSONObj(sysRoles, true, "");
    }

    @LogInfo("根据角色主键查找权限")
    @ApiOperation(value = "根据角色主键查找权限")
    @ApiImplicitParam(name = "enable", value = "是否启用,格式Y|N", paramType = "query", dataType = "string")
    @PostMapping("listAuthorityByRoleId")
    public JSONObject listAuthorityByRoleId(@RequestParam String roleId, String enable) {
        if (StringUtils.isEmpty(roleId))
            return JSONObjResult.toJSONObj("角色主键不能为空");
        SysRole sysRole = sysRoleService.getById(roleId);
        if (sysRole == null)
            return JSONObjResult.toJSONObj("该角色不存在");
        List<SysAuthority> authoritys = sysAuthorityService.listByRoleIdEnable(roleId, enable);
        return JSONObjResult.toJSONObj(authoritys, true, "");
    }

    @LogInfo("保存角色权限")
    @ApiOperation(value = "保存角色权限")
    @PostMapping("saveAuthority")
    public JSONObject saveAuthority(@RequestParam String authorityIds, @RequestParam String roleId) {
        if (StringUtils.isEmpty(authorityIds))
            return JSONObjResult.toJSONObj("权限主键集合不能为空");
        if (StringUtils.isEmpty(roleId))
            return JSONObjResult.toJSONObj("角色主键不能为空");
        SysRole sysRole = sysRoleService.getById(roleId);
        if (sysRole == null)
            return JSONObjResult.toJSONObj("该角色不存在");
        String ids[] = authorityIds.split(",");
        List<SysRoleAuthority> list = new ArrayList<>();
        SysRoleAuthority roleAuthority;
        for (String id : ids) {
            roleAuthority = new SysRoleAuthority();
            roleAuthority.setRoleId(roleId);
            roleAuthority.setAuthorityId(id);
            list.add(roleAuthority);
        }
        RestMessage restMessage = sysRoleAuthorityService.saveBatch(list, roleId);
        return JSONObjResult.toJSONObj(restMessage);
    }
}
