package com.xwbing.service.sys;

import com.xwbing.constant.CommonConstant;
import com.xwbing.entity.SysAuthority;
import com.xwbing.entity.SysRoleAuthority;
import com.xwbing.entity.vo.SysAuthVo;
import com.xwbing.exception.BusinessException;
import com.xwbing.repository.sys.SysAuthorityRepository;
import com.xwbing.util.PassWordUtil;
import com.xwbing.util.RestMessage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2017/11/14 13:20
 * 作者: xiangwb
 * 说明:
 */
@Service
public class SysAuthorityService {
    @Resource
    private SysAuthorityRepository sysAuthorityRepository;
    @Resource
    private SysRoleAuthorityService sysRoleAuthorityService;

    /**
     * 保存权限
     *
     * @param sysAuthority
     * @return
     */
    public RestMessage save(SysAuthority sysAuthority) {
        RestMessage result = new RestMessage();
        boolean b = uniqueCode(sysAuthority.getCode(), null);
        if (!b)
            throw new BusinessException("该编码已存在");
        String id = PassWordUtil.createId();
        sysAuthority.setId(id);
        sysAuthority.setCreateTime(new Date());
        if (StringUtils.isEmpty(sysAuthority.getParentId()))
            sysAuthority.setParentId(CommonConstant.ROOT);
        SysAuthority save = sysAuthorityRepository.save(sysAuthority);
        if (save != null) {
            result.setSuccess(true);
            result.setId(id);
            result.setMessage("保存权限成功");
        } else {
            result.setMessage("保存权限失败");
        }
        return result;
    }

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
    public RestMessage removeById(String id) {
        RestMessage result = new RestMessage();
        SysAuthority one = getById(id);
        if (one == null)
            throw new BusinessException("该权限不存在");
        //删除自身
        sysAuthorityRepository.delete(id);
        //如果有子节点,递归删除子节点
        List<SysAuthority> list = listChildrenForRemove(id);
        if (CollectionUtils.isNotEmpty(list))
            sysAuthorityRepository.deleteInBatch(list);
        result.setMessage("删除成功");
        result.setSuccess(true);
        return result;
    }

    /**
     * 修改权限
     *
     * @param sysAuthority
     * @return
     */
    public RestMessage update(SysAuthority sysAuthority) {
        RestMessage result = new RestMessage();
        String id = sysAuthority.getId();
        SysAuthority old = getById(id);
        if (old == null)
            throw new BusinessException("该权限不存在");
        boolean b = uniqueCode(sysAuthority.getCode(), id);
        if (!b)
            throw new BusinessException("该编码已存在");
        old.setName(sysAuthority.getName());
        old.setCode(sysAuthority.getCode());
        old.setEnable(sysAuthority.getEnable());
        old.setUrl(sysAuthority.getUrl());
        old.setType(sysAuthority.getType());
        SysAuthority save = sysAuthorityRepository.save(old);
        if (save != null) {
            result.setSuccess(true);
            result.setId(id);
            result.setMessage("修改权限成功");
        } else {
            result.setMessage("修改权限失败");
        }
        return result;
    }

    /**
     * 根据主键查找
     *
     * @param id
     * @return
     */
    public SysAuthority getById(String id) {
        return sysAuthorityRepository.findOne(id);
    }

    /**
     * 根据状态获取所有权限
     *
     * @param enable
     * @return
     */
    public List<SysAuthority> listByEnable(String enable) {
        if (StringUtils.isNotEmpty(enable))
            return sysAuthorityRepository.getByEnable(enable);
        else
            return sysAuthorityRepository.findAll();
    }

    /**
     * 根据状态查询所有子节点
     *
     * @param parentId
     * @param enable
     * @return
     */
    public List<SysAuthority> listByParentEnable(String parentId, String enable) {
        if (StringUtils.isNotEmpty(enable))
            return sysAuthorityRepository.getByParentIdAndEnable(parentId, enable);
        else
            return sysAuthorityRepository.getByParentId(parentId);
    }

    /**
     * 根据角色id，是否启用查询权限列表
     *
     * @param roleId
     * @param enable
     * @return
     */
    public List<SysAuthority> listByRoleIdEnable(String roleId, String enable) {
        List<SysAuthority> list = new ArrayList<>();
        List<SysRoleAuthority> roleAuthorities = sysRoleAuthorityService.listByRoleId(roleId);
        if (CollectionUtils.isEmpty(roleAuthorities))
            return list;
        List<String> authorityIds = roleAuthorities.stream().map(SysRoleAuthority::getAuthorityId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(authorityIds))
            if (StringUtils.isNotEmpty(enable))
                list = sysAuthorityRepository.getByEnableAndIdIn(enable, authorityIds);
            else
                list = sysAuthorityRepository.getByIdIn(authorityIds);
        return list;
    }


    /**
     * 根据父节点禁用所有子节点
     *
     * @param parentId
     * @return
     */
    public boolean disableChildrenByParentId(String parentId) {
        List<SysAuthority> sysAuthorities = listChildren(parentId);
        if (CollectionUtils.isNotEmpty(sysAuthorities)) {
            List<SysAuthority> save = sysAuthorityRepository.save(sysAuthorities);
            return CollectionUtils.isNotEmpty(save);
        }
        return false;
    }

    /**
     * 递归查询所有节点（包括禁用）
     *
     * @param parentId
     * @param enable
     * @return
     */
    public List<SysAuthVo> listChildren(String parentId, String enable) {
        List<SysAuthVo> list = new ArrayList<>();
        List<SysAuthority> authoritys;
        if (StringUtils.isNotEmpty(enable))
            authoritys = sysAuthorityRepository.getByParentIdAndEnable(parentId, enable);
        else
            authoritys = sysAuthorityRepository.getByParentId(parentId);
        if (CollectionUtils.isEmpty(authoritys))
            return list;
        SysAuthVo vo;
        for (SysAuthority authority : authoritys) {
            vo = new SysAuthVo(authority);
            vo.setChildren(listChildren(vo.getId(), enable));
            list.add(vo);
        }
        return list;
    }

    /**
     * 递归查询父节点下所有权限的id集合,并将状态设置为禁用(禁用权限时用)
     *
     * @param parentId
     * @return
     */
    private List<SysAuthority> listChildren(String parentId) {
        //获取结果
        List<SysAuthority> sysAuthoritys = listByParentEnable(parentId, CommonConstant.ISENABLE);
        //遍历子集
        List<SysAuthority> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sysAuthoritys)) {
            for (SysAuthority sysAuthority : sysAuthoritys) {
                sysAuthority.setEnable(CommonConstant.ISNOTENABLE);
                sysAuthority.setModifiedTime(new Date());
                list.add(sysAuthority);
                list.addAll(listChildren(sysAuthority.getId()));
            }
        }
        return list;
    }

    /**
     * 刪除时递归获取所有子节点
     *
     * @param parentId
     * @return
     */
    private List<SysAuthority> listChildrenForRemove(String parentId) {
        //获取结果
        List<SysAuthority> sysAuthoritys = listByParentEnable(parentId, null);
        //遍历子集
        List<SysAuthority> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sysAuthoritys)) {
            for (SysAuthority sysAuthority : sysAuthoritys) {
                list.add(sysAuthority);
                list.addAll(listChildren(sysAuthority.getId()));
            }
        }
        return list;
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
        SysAuthority one = sysAuthorityRepository.getByCode(code);
        if (one != null) {
            return StringUtils.isNotEmpty(id) && id.equals(one.getId());
        } else {
            return true;
        }
    }
}
