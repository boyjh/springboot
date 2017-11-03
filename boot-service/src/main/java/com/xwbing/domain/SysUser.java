package com.xwbing.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * 说明: 用户
 * 项目名称: sbdemo
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
     * 密码盐长度
     */
    public static final int SALT_SIZE = 10;
    /**
     * 初始默认密码长度
     */
    public static final int PWD_LENGTH = 8;
    /**
     * hash
     */
    public static int HASH_INTERATIONS = 1024;
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
     * 最后登录IP
     */
    @Column(name = "last_login_ip")
    private String lastLoginIp;
    /**
     * 最后登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;
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
    /**
     * 是否管理员
     */
    @Column(name = "is_admin")
    private String isAdmin;
}
