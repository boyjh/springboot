package com.xwbing.service;

import com.xwbing.entity.SysRole;
import com.xwbing.exception.BusinessException;
import com.xwbing.repository.SysRoleRepository;
import com.xwbing.util.PassWordUtil;
import com.xwbing.util.RestMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2017/11/14 9:24
 * 作者: xiangwb
 * 说明:
 */
@Service
public class SysRoleService {
    @Resource
    private SysRoleRepository sysRoleRepository;
    private final Logger logger = LoggerFactory.getLogger(SysRoleService.class);

    /**
     * 保存角色
     *
     * @param sysRole
     * @return
     */
    public RestMessage save(SysRole sysRole) {
        RestMessage result = new RestMessage();
        boolean b = uniqueCode(sysRole.getCode(), "");
        if (!b)
            throw new BusinessException("该编码已存在");
        String id = PassWordUtil.createId();
        sysRole.setId(id);
        sysRole.setCreateTime(new Date());
        SysRole save = sysRoleRepository.save(sysRole);
        if (save != null) {
            result.setSuccess(true);
            result.setId(id);
            result.setMessage("保存角色成功");
        } else {
            result.setMessage("保存角色失败");
        }
        return result;
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    public RestMessage removeById(String id) {
        RestMessage result = new RestMessage();
        SysRole one = getById(id);
        if (one == null)
            throw new BusinessException("该角色不存在");
        sysRoleRepository.delete(id);
        result.setMessage("删除成功");
        result.setSuccess(true);
        return result;
    }

    /**
     * 修改角色
     *
     * @param sysRole
     * @return
     */
    public RestMessage update(SysRole sysRole) {
        RestMessage result = new RestMessage();
        String id = sysRole.getId();
        SysRole old = getById(id);
        if (old == null) {
            throw new BusinessException("该角色不存在");
        }
        boolean b = uniqueCode(sysRole.getCode(), id);
        if (!b)
            throw new BusinessException("该编码已存在");
        old.setModifiedTime(new Date());
        old.setName(sysRole.getName());
        old.setCode(sysRole.getCode());
        old.setEnable(sysRole.getEnable());
        old.setRemark(sysRole.getRemark());
        SysRole save = sysRoleRepository.save(old);
        if (save != null) {
            result.setSuccess(true);
            result.setId(id);
            result.setMessage("修改角色成功");
        } else {
            result.setMessage("修改角色失败");
        }
        return result;
    }

    /**
     * 根据主键查找
     *
     * @param id
     * @return
     */
    public SysRole getById(String id) {
        return sysRoleRepository.findOne(id);
    }

    /**
     * 根据是否启用列表查询
     *
     * @param enable
     * @return
     */
    public List<SysRole> listAllByEnable(String enable) {
        return sysRoleRepository.getByEnable(enable);
    }

    /**
     * 检查code是否唯一 true唯一
     *
     * @param code
     * @param id
     * @return
     */
    private boolean uniqueCode(String code, String id) {
        if (StringUtils.isEmpty(code))
            throw new BusinessException("code不能为空");
        SysRole one = sysRoleRepository.getByCode(code);
        if (one != null) {
            return StringUtils.isNotEmpty(id) && id.equals(one.getId());
        } else {
            return true;
        }
    }
}
