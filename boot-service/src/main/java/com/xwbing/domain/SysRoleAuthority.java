package com.xwbing.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 说明: 角色权限关系
 * 项目名称: sbdemo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "sys_role_authority")
public class SysRoleAuthority extends BaseEntity {
    private static final long serialVersionUID = 1241064470335852429L;
    public static String table = "sys_role_authority";
    /**
     * 角色主键
     */
    @NotBlank(message = "角色主键不能为空")
    @Column(name = "role_id")
    private String roleId;
    /**
     * 权限主键
     */
    @NotBlank(message = "权限主键不能为空")
    @Column(name = "authority_id")
    private String authorityId;
}
