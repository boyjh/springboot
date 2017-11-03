package com.xwbing.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

/**
 * 说明: 角色
 * 项目名称: sbdemo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "sys_role")
public class SysRole extends BaseEntity {
    private static final long serialVersionUID = -3048021197170624143L;
    public static String table = "sys_role";
    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String name;
    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空")
    @Length(min = 6, max = 50, message = "长度为6-20")
    private String code;
    /**
     * 是否启用
     */
    @NotBlank(message = "是否启用不能为空")
    @Pattern(regexp = "Y|N",message = "是否启用格式应为Y|N")
    @Column(name = "is_enable")
    private String isEnable;
    /**
     * 描述
     */
    private String remark;
}
