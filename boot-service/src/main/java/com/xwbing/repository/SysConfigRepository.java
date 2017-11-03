package com.xwbing.repository;

import com.xwbing.domain.SysConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 浙江卓锐科技股份有限公司 版权所有 ? Copyright 2016<br/>
 * 说明: <br/>
 * 项目名称: ${project_name} <br/>
 * 创建日期: ${date} ${time} <br/>
 * 作者: wdz
 */
public interface SysConfigRepository extends JpaRepository<SysConfig,String> {
    SysConfig findByCode(String code);
}
