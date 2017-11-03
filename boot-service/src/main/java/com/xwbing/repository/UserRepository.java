package com.xwbing.repository;

import com.xwbing.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<SysUser, String> {
    List<SysUser> findByUserName(String userName);

}
