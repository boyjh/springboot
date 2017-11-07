package com.xwbing.controller;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.annotation.LogInfo;
import com.xwbing.entity.SysUser;
import com.xwbing.service.UserService;
import com.xwbing.util.JSONObjResult;
import com.xwbing.util.RestMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 说明: 用户控制层
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
@RestController
@RequestMapping("/user/")
public class UserControl {
    @Resource
    private UserService userService;

    /**
     * 添加用户
     *
     * @param sysUser
     * @return
     */
    @LogInfo("添加用户")
    @PostMapping("save")
    public JSONObject save(@RequestBody @Valid SysUser sysUser) {
        RestMessage result = userService.save(sysUser);
        return JSONObjResult.toJSONObj(result);
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @LogInfo("删除用户")
    @GetMapping("removeById")
    public JSONObject removeById(@RequestParam String id) {
        if (StringUtils.isEmpty(id)) {
            return JSONObjResult.toJSONObj("主键不能为空");
        }
        RestMessage result = userService.removeById(id);
        return JSONObjResult.toJSONObj(result);
    }

    /**
     * 修改用户信息
     *
     * @param sysUser
     * @return
     */
    @LogInfo("修改用户信息")
    @PostMapping("update")
    public JSONObject update(@RequestBody @Valid SysUser sysUser) {
        if (StringUtils.isEmpty(sysUser.getId())) {
            return JSONObjResult.toJSONObj("主键不能为空");
        }
        RestMessage result = userService.update(sysUser);
        return JSONObjResult.toJSONObj(result);
    }

    /**
     * 获取用户详情
     *
     * @param id
     * @return
     */
    @LogInfo("获取用户详情")
    @GetMapping("findById")
    public JSONObject findById(@RequestParam String id) {
        if (StringUtils.isEmpty(id)) {
            return JSONObjResult.toJSONObj("主键不能为空");
        }
        SysUser sysUser = userService.findOne(id);
        if (sysUser == null) {
            return JSONObjResult.toJSONObj("未查到该对象");
        }
        return JSONObjResult.toJSONObj(sysUser, true, "");
    }

    /**
     * 列表查询所有用户
     *
     * @return
     */
    @LogInfo("列表查询所有用户")
    @GetMapping("findList")
    public JSONObject findList() {
        List<SysUser> list = userService.listAll();
        return JSONObjResult.toJSONObj(list, true, "");
    }

    /**
     * 登录
     *
     * @param userName
     * @param passWord
     * @param checkCode
     * @return
     */
    @LogInfo("登录")
    @GetMapping("login")
    public JSONObject login(@RequestParam String userName, @RequestParam String passWord, @RequestParam String checkCode) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWord))
            return JSONObjResult.toJSONObj("用户名或密码不能为空");
        if (StringUtils.isEmpty(checkCode))
            return JSONObjResult.toJSONObj("请输入验证码");
        RestMessage login = userService.login(userName, passWord, checkCode);
        return JSONObjResult.toJSONObj(login);
    }

    /**
     * 修改密码
     *
     * @param newPassWord
     * @param oldPassWord
     * @return
     */
    @LogInfo("修改密码")
    @GetMapping("updatePassWord")
    public JSONObject updatePassWord(@RequestParam String newPassWord, @RequestParam String oldPassWord, @RequestParam String id) {
        if (StringUtils.isEmpty(newPassWord) || StringUtils.isEmpty(oldPassWord))
            return JSONObjResult.toJSONObj("原密码或新密码不能为空");
        if (StringUtils.isEmpty(id))
            return JSONObjResult.toJSONObj("主键不能为空");
        RestMessage restMessage = userService.updatePassWord(newPassWord, oldPassWord, id);
        return JSONObjResult.toJSONObj(restMessage);
    }

    @LogInfo("重置密码")
    @GetMapping("resetPassWord")
    public JSONObject resetPassWord(@RequestParam String id) {
        if (StringUtils.isEmpty(id))
            return JSONObjResult.toJSONObj("主键不能为空");
        RestMessage restMessage = userService.resetPassWord(id);
        return JSONObjResult.toJSONObj(restMessage);
    }
}
