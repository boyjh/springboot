package com.xwbing.service;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.constant.CommonConstant;
import com.xwbing.entity.SysConfig;
import com.xwbing.entity.SysUser;
import com.xwbing.entity.model.EmailModel;
import com.xwbing.exception.BusinessException;
import com.xwbing.repository.UserRepository;
import com.xwbing.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 说明:
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/5 16:44
 * 作者:  xiangwb
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 增
     *
     * @param sysUser
     * @return
     */
    public RestMessage save(SysUser sysUser) {
        RestMessage result = new RestMessage();
        SysUser old = findByUserName(sysUser.getUserName());
        if (old != null) {
            throw new BusinessException("已经存在此用户名");
        }
        sysUser.setId(PassWordUtil.createId());
        sysUser.setCreateTime(new Date());
        // 获取初始密码
        String[] res = PassWordUtil.getUserSecret(null, null);
        sysUser.setSalt(res[1]);
        sysUser.setPassword(res[2]);
        SysUser one = userRepository.save(sysUser);
        if (one == null) {
            throw new BusinessException("新增用户失败");
        }
        SysConfig sysConfig = sysConfigService.findByCode(CommonConstant.SYSCONFIG_EMAILCONFIGKEY);
        if (Objects.isNull(sysConfig)) {
            throw new BusinessException("没有查找到邮件配置项");
        }
        EmailModel emailModel = null;
        if (StringUtils.isNotEmpty(sysConfig.getCode())) {
            JSONObject jsonObject = JSONObject.parseObject(sysConfig.getValue());
            emailModel = JSONObject.toJavaObject(jsonObject, EmailModel.class);
        }
        emailModel.setToEmail(sysUser.getMail());
        emailModel.setSubject(emailModel.getSubject());
        emailModel.setCentent(emailModel.getCentent() + " 你的用户名是：" + sysUser.getUserName() + "密码是：" + res[0]);
        // 发送邮件结束
        boolean sucess = EmailUtil.sendTextEmail(emailModel);
        if (!sucess) {
            throw new BusinessException("发送密码邮件错误");
        }
        result.setSuccess(true);
        return result;
    }

    /**
     * 删
     *
     * @param id
     * @return
     */
    public RestMessage removeById(String id) {
        RestMessage result = new RestMessage();
        SysUser old = findOne(id);
        if (old == null) {
            throw new BusinessException("该对象不存在");
        }
        userRepository.delete(id);
        result.setSuccess(true);
        return result;

    }

    /**
     * 更新
     *
     * @param sysUser
     * @return
     */
    public RestMessage update(SysUser sysUser) {
        RestMessage result = new RestMessage();
        SysUser old = findOne(sysUser.getId());
        if (old == null) {
            throw new BusinessException("该对象不存在");
        }
        // 根据实际情况补充
        old.setName(sysUser.getName());
        old.setMail(sysUser.getMail());
        old.setSex(sysUser.getSex());
        old.setUserName(sysUser.getUserName());
        SysUser one = userRepository.save(old);
        if (one == null) {
            result.setMessage("更新用户成功");
            result.setSuccess(true);
        } else {
            result.setMessage("更新用户失败");
        }
        return result;
    }

    /**
     * 单个查询
     *
     * @param id
     * @return
     */
    public SysUser findOne(String id) {
        return userRepository.findOne(id);
    }

    /**
     * 列表查询
     *
     * @return
     */
    public List<SysUser> findList() {
        return userRepository.findAll();
    }

    /**
     * 修改密码
     *
     * @param newPassWord
     * @param oldPassWord
     * @param loginUserId
     * @return
     */
    public RestMessage updatePassWord(String newPassWord, String oldPassWord, String loginUserId) {
        RestMessage result = new RestMessage();
        SysUser sysUser = findOne(loginUserId);
        if (sysUser == null)
            throw new BusinessException("该用户不存在");
        boolean flag = checkPassWord(oldPassWord, sysUser.getPassword(), sysUser.getSalt());
        if (!flag)
            throw new BusinessException("原密码错误，请重新输入");
        String[] str = PassWordUtil.getUserSecret(newPassWord, null);
        sysUser.setSalt(str[1]);
        sysUser.setPassword(str[2]);
        SysUser one = userRepository.save(sysUser);
        if (Objects.nonNull(one)) {
            result.setMessage("更新密码成功");
            result.setSuccess(true);
        } else {
            result.setMessage("更新密码失败");
        }
        return result;
    }

    /**
     * 登录
     *
     * @param req
     * @param userName
     * @param passWord
     * @param ckeckCode
     * @return
     */
    public RestMessage login(HttpServletRequest req, String userName, String passWord, String ckeckCode) {
        RestMessage restMessage = new RestMessage();
        HttpSession session = req.getSession();
        String imgCode = (String) session.getAttribute(CommonConstant.KEY_CAPTCHA);
        //验证验证码
        if (!ckeckCode.equalsIgnoreCase(imgCode) || StringUtils.isEmpty(ckeckCode)) {
            throw new BusinessException("验证码错误");
        }
        //验证账号,密码
        SysUser user = findByUserName(userName);
        if (user == null)
            throw new BusinessException("账号错误");
        boolean flag = checkPassWord(passWord, user.getPassword(), user.getSalt());
        if (!flag)
            throw new BusinessException("密码错误");
        session.setAttribute(CommonConstant.SESSION_CURRENT_USER, userName);
        restMessage.setSuccess(true);
        restMessage.setMessage("登录成功");
        return restMessage;
    }

    /**
     * 根据用户名查找用户
     *
     * @param userName
     * @return
     */
    private SysUser findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    /**
     * 校验密码
     *
     * @param realPassWord
     * @param passWord
     * @param salt
     * @return
     */
    private boolean checkPassWord(String passWord, String realPassWord, String salt) {
        boolean flag;
        // 根据密码盐值， 解码
        byte[] saltByte = EncodeUtils.hexDecode(salt);
        byte[] hashPassword = Digests.sha1(passWord.getBytes(), saltByte,
                SysUser.HASH_INTERATIONS);
        // 密码 数据库中密码
        String validatePassWord = EncodeUtils.hexEncode(hashPassword);
        //判断密码是否相同
        flag = realPassWord.equals(validatePassWord);
        return flag;
    }

}
