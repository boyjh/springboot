package com.xwbing.domain.entity.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

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
@ApiModel
public class SysConfig extends BaseEntity {
    private static final long serialVersionUID = -7587016038432881980L;
    public static String table = "system_config";
    @NotBlank(message = "配置项的code不能为空")
    @Length(min = 1, max = 50, message = "code长度为1-50")
    @ApiModelProperty(value = "配置项的code", example = "email_config", required = true)
    private String code;
    @NotBlank(message = "配置项的value不能为空")
    @ApiModelProperty(value = "配置项的值", example = "{}", required = true)
    private String value;
    @NotBlank(message = "配置项的name不能为空")
    @Length(min = 1, max = 20, message = "value长度为1-20")
    @ApiModelProperty(value = "配置项的描述(名称)", example = "邮箱配置", required = true)
    private String name;
    @NotBlank(message = "是否启用不能为空")
    @Pattern(regexp = "[Y|N]", message = "是否启用格式为Y|N")
    @ApiModelProperty(value = "是否启用(Y|N)", example = "Y", required = true)
    private String enable;
}
