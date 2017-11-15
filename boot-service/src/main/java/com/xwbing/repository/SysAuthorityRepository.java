package com.xwbing.repository;

import com.xwbing.entity.SysAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 说明:
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/5 16:44
 * 作者:  xiangwb
 */
public interface SysAuthorityRepository extends JpaRepository<SysAuthority, String> {
    SysAuthority getByCode(String code);

    List<SysAuthority> getByEnable(String enable);

    List<SysAuthority> getByParentIdAndEnable(String parentId, String enable);

    List<SysAuthority> getByParentId(String parentId);

    List<SysAuthority> getByEnableAndIdIn(String enable, List<String> ids);

    List<SysAuthority> getByIdIn(List<String> ids);
}
