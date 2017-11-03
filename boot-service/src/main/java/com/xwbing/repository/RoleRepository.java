package com.xwbing.repository;

import com.xwbing.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by admin on 2017/4/11.
 */
public interface RoleRepository extends JpaRepository<SysRole,String> {
}
