package com.xwbing.repository.sys;

import com.xwbing.entity.sys.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2017/11/14 14:13
 * 作者: xiangwb
 * 说明:
 */
public interface SysUserRoleRepository extends JpaRepository<SysUserRole, String> {
    List<SysUserRole> getByUserId(String userId);
}
