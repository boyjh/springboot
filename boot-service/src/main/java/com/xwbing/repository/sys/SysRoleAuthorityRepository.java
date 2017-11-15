package com.xwbing.repository.sys;

import com.xwbing.entity.SysRoleAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2017/11/14 15:13
 * 作者: xiangwb
 * 说明:
 */
public interface SysRoleAuthorityRepository extends JpaRepository<SysRoleAuthority, String> {
    List<SysRoleAuthority> getByRoleId(String roleId);
}
