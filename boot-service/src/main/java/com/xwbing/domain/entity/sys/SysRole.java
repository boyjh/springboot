package com.xwbing.domain.entity.sys;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

/**
 * 说明: 角色
 * 项目名称: boot-module-demo
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
    private String name;
    /**
     * 角色编码
     */
    private String code;
    /**
     * 是否启用
     */
    private String enable;
    /**
     * 描述
     */
    private String remark;
}
