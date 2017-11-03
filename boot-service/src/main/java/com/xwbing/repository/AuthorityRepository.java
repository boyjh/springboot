package com.xwbing.repository;

import com.xwbing.domain.SysAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface AuthorityRepository extends JpaRepository<SysAuthority,String> {
}
