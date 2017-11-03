package com.xwbing.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * 说明: 基础类
 * 项目名称: sbdemo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
@Data
@MappedSuperclass
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 8901948362657956187L;
    @Id
    @Column(length = 50)
    private String id;
    private String creator;
    private String modifier;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modified_time")
    private Date modifiedTime;
    @Column(name = "is_deleted")
    private String isDeleted;
    private Integer sort;
}
