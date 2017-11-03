package com.xwbing.service;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.constant.CommonConstant;
import com.xwbing.constant.CommonEnum;
import com.xwbing.domain.SysConfig;
import com.xwbing.domain.SysUser;
import com.xwbing.domain.model.EmailModel;
import com.xwbing.exception.BusinessException;
import com.xwbing.repository.UserRepository;
import com.xwbing.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 说明:
 * 项目名称: sbdemo
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
     * @param sysUser
     * @return
     */
    public RestMessage save(SysUser sysUser) {
        RestMessage result = new RestMessage();
        SysUser old = findByUserName(sysUser.getUserName());
        if (Objects.nonNull(old)) {
            throw new BusinessException("已经存在此用户名");
        }
        sysUser.setId(PassWordUtil.createId());
        sysUser.setCreateTime(new Date());
        // 设置否管理员
        sysUser.setIsAdmin(CommonEnum.YesOrNo.NO.getCode());
        // 获取初始密码
        String[] res = PassWordUtil.getUserSecret(null, null);
        sysUser.setSalt(res[1]);
        sysUser.setPassword(res[2]);
        SysUser one = userRepository.save(sysUser);
        if (Objects.isNull(one)) {
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
     * @param id
     * @return
     */
    public RestMessage removeById(String id) {
        RestMessage result = new RestMessage();
        SysUser old=findOne(id);
        if(Objects.isNull(old)){
            throw new BusinessException("该对象不存在");
        }
        if (CommonEnum.YesOrNo.YES.getCode().equalsIgnoreCase(old.getIsAdmin())) {
            throw new BusinessException("不能对管理员进行删除操作");
        }
        userRepository.delete(id);
        result.setSuccess(true);
        return result;

    }

    /**
     * 更新
     * @param sysUser
     * @return
     */
    public RestMessage update(SysUser sysUser){
        RestMessage result=new RestMessage();
        SysUser old=findOne(sysUser.getId());
        if(Objects.isNull(old)){
            throw new BusinessException("该对象不存在");
        }
        // 根据实际情况补充
        old.setName(sysUser.getName());
        old.setMail(sysUser.getMail());
        old.setSex(sysUser.getSex());
        old.setUserName(sysUser.getUserName());
        SysUser one=userRepository.save(old);
        if(Objects.nonNull(one)){
            result.setMsg("更新用户成功");
            result.setSuccess(true);
        }else {
            result.setMsg("更新用户失败");
        }
        return result;
    }
    /**
     * 单个查询
     * @param id
     * @return
     */
    public SysUser findOne(String id) {
        return userRepository.findOne(id);
    }

    /**
     * 列表查询
     * @return
     */
    public List<SysUser> findList() {
        List<SysUser> list = null;
        list = userRepository.findAll();
        return list;
    }

    /**
     * 根据用户名查找用户
     *
     * @param userName
     * @return
     */
    public SysUser findByUserName(String userName) {
        SysUser sysUser = null;
        List<SysUser> list = userRepository.findByUserName(userName);
        if (CollectionUtils.isNotEmpty(list)) {
            sysUser = list.get(0);
        }
        return sysUser;
    }
    public RestMessage updatePassWord(String newPassWord, String oldPassWord,
                                      String loginUserId){
        RestMessage result=new RestMessage();
        SysUser sysUser=findOne(loginUserId);
        // 根据密码盐值， 解码
        byte[] salt = EncodeUtils.hexDecode(sysUser.getSalt());
        byte[] hashPassword = Digests.sha1(oldPassWord.getBytes(), salt,
                SysUser.HASH_INTERATIONS);
        // 密码 数据库中密码
        String validatePassWord = EncodeUtils.hexEncode(hashPassword);
        if (!Objects.equals(validatePassWord,sysUser.getPassword())) {// 如果不相等
            throw new BusinessException("原密码错误，请重新输入");
        }
        String[] str = PassWordUtil.getUserSecret(newPassWord, null);
        sysUser.setSalt(str[1]);
        sysUser.setPassword(str[2]);
        SysUser one=userRepository.save(sysUser);
        if(Objects.nonNull(one)){
            result.setMsg("更新密码成功");
            result.setSuccess(true);
        }else {
            result.setMsg("更新密码失败");
        }
        return result;

    }

}
