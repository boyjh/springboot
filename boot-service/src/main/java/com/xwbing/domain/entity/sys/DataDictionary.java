package com.xwbing.domain.entity.sys;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/2/26 10:26
 * 作者: xiangwb
 * 说明: 数据字典
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "data_dict")
public class DataDictionary extends BaseEntity {
    private static final long serialVersionUID = -3409347240188002427L;
    public static String table = "data_dict";
    /**
     * 英文名称  编码 唯一
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 父键
     */
    @Column(name = "parent_id")
    private String parentId;
    /**
     * 是否启用
     */
    private String enable;
}
