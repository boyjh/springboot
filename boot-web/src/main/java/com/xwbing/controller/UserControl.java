package com.xwbing.controller;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.entity.SysUser;
import com.xwbing.service.UserService;
import com.xwbing.util.JSONObjResult;
import com.xwbing.util.RestMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * 说明: user
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
@RestController
@RequestMapping("/user/")
public class UserControl {
    private final static Logger log = LoggerFactory.getLogger(UserControl.class);
    @Autowired
    private UserService userService;

    @PostMapping("save")
    public JSONObject save(@Valid SysUser sysUser) {
        RestMessage result = userService.save(sysUser);
        return JSONObjResult.toJSONObj(result);
    }

    @PostMapping("removeById")
    public JSONObject removeById(@RequestParam String id) {
        if (StringUtils.isEmpty(id)) {
            return JSONObjResult.toJSONObj("主键不能为空");
        }
        RestMessage result = userService.removeById(id);
        return JSONObjResult.toJSONObj(result);
    }

    @PostMapping("update")
    public JSONObject update(SysUser sysUser) {
        if (StringUtils.isEmpty(sysUser.getId())) {
            return JSONObjResult.toJSONObj("主键不能为空");
        }
        RestMessage result = userService.update(sysUser);
        return JSONObjResult.toJSONObj(result);
    }

    @GetMapping("findById")
    public JSONObject findById(@RequestParam String id) {
        if (StringUtils.isEmpty(id)) {
            return JSONObjResult.toJSONObj("主键不能为空");
        }
        SysUser sysUser = userService.findOne(id);
        if (Objects.isNull(sysUser)) {
            return JSONObjResult.toJSONObj("未查到该对象");
        }
        return JSONObjResult.toJSONObj(sysUser, true, "");
    }

    @GetMapping("findList")
    public JSONObject findList() {
        List<SysUser> list = userService.listAll();
        return JSONObjResult.toJSONObj(list, true, "");
    }

    @GetMapping("login")
    public JSONObject login(HttpServletRequest req, @RequestParam String userName, @RequestParam String passWord,@RequestParam String checkCode) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWord))
            return JSONObjResult.toJSONObj("用户名或密码不能为空");
        if (StringUtils.isEmpty(checkCode) )
            return JSONObjResult.toJSONObj("请输入验证码");
        RestMessage login = userService.login(req, userName, passWord, checkCode);
        return JSONObjResult.toJSONObj(login);
    }

    @GetMapping("updatePassWord")
    public JSONObject updatePassWord(HttpServletRequest request, @RequestParam String newPassWord, @RequestParam String oldPassWord) {
        if (StringUtils.isEmpty(newPassWord))
            return JSONObjResult.toJSONObj("新密码不能为空");
        if (StringUtils.isEmpty(oldPassWord))
            return JSONObjResult.toJSONObj("旧密码不能为空");
        RestMessage restMessage = userService.updatePassWord(request, newPassWord, oldPassWord);
        return JSONObjResult.toJSONObj(restMessage);
    }
}
