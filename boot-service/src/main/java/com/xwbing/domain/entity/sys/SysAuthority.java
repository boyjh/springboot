package com.xwbing.domain.entity.sys;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 说明: 权限
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "sys_authority")
public class SysAuthority extends BaseEntity {
    private static final long serialVersionUID = -6469518352117371987L;
    public static String table = "sys_authority";
    /**
     * 名称
     */
    private String name;
    /**
     * 编号
     */
    private String code;
    /**
     * 是否启用
     */
    private String enable;
    /**
     * url地址
     */
    private String url;
    /**
     * 父ID
     */
    @Column(name = "parent_id")
    private String parentId;
    /**
     * 类型  2按钮 1菜单
     */
    private Integer type;
}
