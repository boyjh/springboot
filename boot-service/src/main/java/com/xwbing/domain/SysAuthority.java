package com.xwbing.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

/**
 * 说明: 权限
 * 项目名称: sbdemo
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
    @NotBlank(message = "name不能为空")
    @Length(min = 1, max = 10, message = "name长度为1-10")
    private String name;
    /**
     * 编号
     */
    @NotBlank(message = "编号不能为空")
    @Length(min = 6, max = 50, message = "编号长度为6-50")
    private String code;
    /**
     * 是否启用
     */
    @NotBlank(message = "是否启用不能为空")
    @Pattern(regexp = "Y|N",message = "是否启用格式应为Y|N")
    @Column(name = "is_enable")
    private String isEnable;
    /**
     * url地址
     */
    private String url;
    /**
     * 父ID
     */
    @NotBlank(message = "父ID不能为空")
    @Length(min = 6, max = 50, message = "父ID长度为6-50")
    @Column(name = "parent_id")
    private String parentId;
    /**
     * 类型  2按钮 1菜单
     */
    private Integer type;
}
