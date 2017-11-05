package com.xwbing.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

/**
 * 说明: 用户
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "sys_user_info")
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = -2447528751353457021L;
    public static String table = "sys_user_info";
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Length(min = 1, max = 20, message = "长度为1-20")
    @Column(name = "user_name")
    private String userName;
    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @Length(min = 1, max = 20, message = "长度为1-5")
    private String name;
    /**
     * 盐值
     */
    private String salt;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱
     */
    @Email
    private String mail;
    /**
     * 性别
     */
    @NotBlank
    @Pattern(regexp = "[01]", message = "性别格式为0|1,0代表女,1代表男")
    private String sex;
}
