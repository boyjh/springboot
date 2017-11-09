package com.xwbing.service;

import com.xwbing.entity.SysConfig;
import com.xwbing.exception.BusinessException;
import com.xwbing.repository.SysConfigRepository;
import com.xwbing.util.PassWordUtil;
import com.xwbing.util.RestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 说明:
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/5 16:44
 * 作者:  xiangwb
 */
@Service
public class SysConfigService {
    @Resource
    private SysConfigRepository sysConfigRepository;
    private static Logger logger = LoggerFactory.getLogger(SysConfigService.class);

    public RestMessage save(SysConfig sysConfig) {
        logger.info("保存配置信息");
        RestMessage result = new RestMessage();
        if (sysConfig == null) {
            throw new BusinessException("配置数据不能为空");
        }
        SysConfig old = findByCode(sysConfig.getCode());
        if (old != null) {
            throw new BusinessException(sysConfig.getCode() + "已存在");
        }
        sysConfig.setId(PassWordUtil.createId());
        sysConfig.setCreateTime(new Date());
        SysConfig one = sysConfigRepository.save(sysConfig);
        if (one != null) {
            result.setSuccess(true);
            result.setMessage("保存配置成功");
        } else {
            result.setMessage("保存配置失败");
        }
        return result;
    }

    public RestMessage removeByCode(String code) {
        logger.info("删除配置信息");
        RestMessage result = new RestMessage();
        SysConfig old = findByCode(code);
        if (old == null) {
            throw new BusinessException("该配置项不存在");
        }
        sysConfigRepository.delete(old.getId());
        result.setSuccess(true);
        result.setMessage("删除配置成功");
        return result;
    }

    public RestMessage update(SysConfig sysConfig) {
        RestMessage result = new RestMessage();
        SysConfig old = findByCode(sysConfig.getCode());
        if (old == null) {
            throw new BusinessException("该配置项不存在");
        }
        old.setValue(sysConfig.getValue());
        old.setEnable(sysConfig.getEnable());
        old.setModifiedTime(new Date());
        SysConfig save = sysConfigRepository.save(old);
        if (save != null) {
            result.setMessage("更新成功");
            result.setSuccess(true);
        } else {
            result.setMessage("更新失败");
        }
        return result;
    }

    public SysConfig findByCode(String code) {
        return sysConfigRepository.findByCode(code);
    }

    public List<SysConfig> findByEnable(String enable) {
        return sysConfigRepository.findByEnable(enable);
    }
}
