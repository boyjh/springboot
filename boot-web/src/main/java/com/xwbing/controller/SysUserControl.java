package com.xwbing.controller;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.annotation.LogInfo;
import com.xwbing.constant.CommonConstant;
import com.xwbing.constant.CommonEnum;
import com.xwbing.entity.SysAuthority;
import com.xwbing.entity.SysRole;
import com.xwbing.entity.SysUser;
import com.xwbing.entity.SysUserRole;
import com.xwbing.service.SysAuthorityService;
import com.xwbing.service.SysRoleService;
import com.xwbing.service.SysUserRoleService;
import com.xwbing.service.SysUserService;
import com.xwbing.util.CommonDataUtil;
import com.xwbing.util.JSONObjResult;
import com.xwbing.util.RestMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 说明: 用户控制层
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
@Api(tags = "userApi", description = "用户相关接口")
@RestController
@RequestMapping("/user/")
public class SysUserControl {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysAuthorityService sysAuthorityService;

    @LogInfo("添加用户")
    @PostMapping("save")
    public JSONObject save(@RequestBody @Valid SysUser sysUser) {
        RestMessage result = sysUserService.save(sysUser);
        return JSONObjResult.toJSONObj(result);
    }

    @LogInfo("删除用户")
    @GetMapping("removeById")
    public JSONObject removeById(@RequestParam String id) {
        if (StringUtils.isEmpty(id))
            return JSONObjResult.toJSONObj("主键不能为空");
        RestMessage result = sysUserService.removeById(id);
        return JSONObjResult.toJSONObj(result);
    }

    @LogInfo("修改用户信息")
    @PostMapping("update")
    public JSONObject update(@RequestBody @Valid SysUser sysUser) {
        if (StringUtils.isEmpty(sysUser.getId()))
            return JSONObjResult.toJSONObj("主键不能为空");
        RestMessage result = sysUserService.update(sysUser);
        return JSONObjResult.toJSONObj(result);
    }

    @LogInfo("获取用户详情")
    @GetMapping("getById")
    public JSONObject getById(@RequestParam String id) {
        if (StringUtils.isEmpty(id))
            return JSONObjResult.toJSONObj("主键不能为空");
        SysUser sysUser = sysUserService.getOne(id);
        if (sysUser == null)
            return JSONObjResult.toJSONObj("未查到该对象");
        return JSONObjResult.toJSONObj(sysUser, true, "");
    }

    @LogInfo("列表查询所有用户")
    @GetMapping("listAll")
    public JSONObject listAll() {
        List<SysUser> list = sysUserService.listAll();
        return JSONObjResult.toJSONObj(list, true, "");
    }

    @LogInfo("登录")
    @PostMapping("login")
    public JSONObject login(HttpServletRequest request, @RequestParam String userName, @RequestParam String passWord, @RequestParam String checkCode) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWord))
            return JSONObjResult.toJSONObj("用户名或密码不能为空");
        if (StringUtils.isEmpty(checkCode))
            return JSONObjResult.toJSONObj("请输入验证码");
        RestMessage login = sysUserService.login(request, userName, passWord, checkCode);
        return JSONObjResult.toJSONObj(login);
    }

    @LogInfo("登出")
    @GetMapping("logout")
    public JSONObject logout(HttpServletRequest request) {
        RestMessage logout = sysUserService.logout(request);
        return JSONObjResult.toJSONObj(logout);
    }

    @LogInfo("修改密码")
    @PostMapping("updatePassWord")
    public JSONObject updatePassWord(@RequestParam String newPassWord, @RequestParam String oldPassWord, @RequestParam String id) {
        if (StringUtils.isEmpty(id))
            return JSONObjResult.toJSONObj("主键不能为空");
        if (StringUtils.isEmpty(newPassWord) || StringUtils.isEmpty(oldPassWord))
            return JSONObjResult.toJSONObj("原密码或新密码不能为空");
        RestMessage restMessage = sysUserService.updatePassWord(newPassWord, oldPassWord, id);
        return JSONObjResult.toJSONObj(restMessage);
    }

    @LogInfo("重置密码")
    @GetMapping("resetPassWord")
    public JSONObject resetPassWord(@RequestParam String id) {
        if (StringUtils.isEmpty(id))
            return JSONObjResult.toJSONObj("主键不能为空");
        RestMessage restMessage = sysUserService.resetPassWord(id);
        return JSONObjResult.toJSONObj(restMessage);
    }

    @LogInfo("获取当前登录用户信息")
    @GetMapping("getLoginUserInfo")
    public JSONObject getLoginUserInfo() {
        SysUser sysUser = sysUserService.getOne((String) CommonDataUtil.getToken(CommonConstant.CURRENT_USER_ID));
        if (sysUser == null)
            return JSONObjResult.toJSONObj("未获取到当前登录用户信息");
        List<SysAuthority> button = new ArrayList<>();
        List<SysAuthority> menu = new ArrayList<>();
        List<SysAuthority> list;
        if (CommonEnum.YesOrNoEnum.YES.getCode().equalsIgnoreCase(sysUser.getAdmin()))
            list = sysAuthorityService.listByEnable(CommonEnum.YesOrNoEnum.YES.getCode());
        else
            list = sysUserService.queryAuthority(sysUser.getId(), CommonEnum.YesOrNoEnum.YES.getCode());
        if (CollectionUtils.isNotEmpty(list)) {
            for (SysAuthority sysAuthority : list) {
                if (sysAuthority.getType() == CommonEnum.MenuOrButtonEnum.MENU.getCode())
                    menu.add(sysAuthority);
                else
                    button.add(sysAuthority);
            }
        }
        sysUser.setMenuArray(menu);
        sysUser.setButtonArray(button);
        return JSONObjResult.toJSONObj(sysUser, true, "");
    }

    @LogInfo("保存用户角色")
    @PostMapping("saveRole")
    public JSONObject saveRole(@RequestParam String roleIds, @RequestParam String userId) {
        if (StringUtils.isEmpty(roleIds))
            return JSONObjResult.toJSONObj("角色主键不能为空");
        if (StringUtils.isEmpty(userId))
            return JSONObjResult.toJSONObj("用户主键不能为空");
        SysUser old = sysUserService.getOne(userId);
        if (old == null)
            return JSONObjResult.toJSONObj("该用户不存在");
        if (CommonEnum.YesOrNoEnum.YES.getCode().equalsIgnoreCase(old.getAdmin()))
            return JSONObjResult.toJSONObj("不能对管理员进行操作");
        String ids[] = roleIds.split(",");
        List<SysUserRole> list = new ArrayList<>();
        for (String id : ids) {
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleId(id);
            userRole.setUserId(userId);
            list.add(userRole);
        }
        RestMessage restMessage = sysUserRoleService.saveBatch(list, userId);
        return JSONObjResult.toJSONObj(restMessage);
    }

    @LogInfo("根据用户主键查找所拥有的角色")
    @PostMapping("listRoleByUserId")
    @ApiImplicitParam(name = "enable", value = "是否启用,格式Y|N", paramType = "query", dataType = "string")
    public JSONObject listRoleByUserId(@RequestParam String userId, String enable) {
        if (StringUtils.isEmpty(userId))
            return JSONObjResult.toJSONObj("用户主键不能为空");
        List<SysRole> list = sysRoleService.listByUserIdEnable(userId, enable);
        return JSONObjResult.toJSONObj(list, true, "");
    }

    @LogInfo("根据用户主键查找所拥有的权限")
    @PostMapping("listAuthorityByUserId")
    @ApiImplicitParam(name = "enable", value = "是否启用,格式Y|N", paramType = "query", dataType = "string")
    public JSONObject listAuthorityByUserId(@RequestParam String userId, String enable) {
        if (StringUtils.isEmpty(userId))
            return JSONObjResult.toJSONObj("用户主键不能为空");
        List<SysAuthority> list = sysUserService.queryAuthority(userId, enable);
        return JSONObjResult.toJSONObj(list, true, "");
    }
}
