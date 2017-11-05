package com.xwbing.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

/**
 * 说明: 系统配置
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "system_config")
public class SysConfig extends BaseEntity {
    private static final long serialVersionUID = -7587016038432881980L;
    public static String table = "system_config";
    /**
     * 配置项的key
     */
    @NotBlank(message = "配置项的code不能为空")
    @Length(min = 1, max = 50, message = "code长度为1-50")
    private String code;
    /**
     * 配置项的值
     */
    @NotBlank(message = "配置项的value不能为空")
    private String value;
    /**
     * 是否启用
     */
    @NotBlank(message = "是否启用不能为空")
    @Pattern(regexp = "[Y|N]",message = "是否启用格式为Y|N")
    @Column(name = "is_enable")
    private String isEnable;
    /**
     * 配置项的描述（名称）
     */
    @NotBlank(message = "配置项的name不能为空")
    @Length(min = 1, max = 10, message = "value长度为1-10")
    private String name;
}
