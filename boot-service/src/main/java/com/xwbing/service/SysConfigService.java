package com.xwbing.service;

import com.xwbing.domain.SysConfig;
import com.xwbing.exception.BusinessException;
import com.xwbing.repository.SysConfigRepository;
import com.xwbing.util.PassWordUtil;
import com.xwbing.util.RestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * 说明:
 * 项目名称: sbdemo
 * 创建时间: 2017/5/5 16:44
 * 作者:  xiangwb
 */
@Service
public class SysConfigService {
    @Autowired
    private SysConfigRepository sysConfigRepository;
    private  static Logger logger= LoggerFactory.getLogger(SysConfigService.class);
    public RestMessage save(SysConfig sysConfig) {
        logger.info("保存配置信息");
        RestMessage result = new RestMessage();
        sysConfig.setId(PassWordUtil.createId());
        sysConfig.setCreateTime(new Date());
        if (Objects.isNull(sysConfig)) {
            throw new BusinessException("配置数据不能为空");
        }
        SysConfig old = findByCode(sysConfig.getCode());
        if (Objects.nonNull(old)) {
            throw new BusinessException(sysConfig.getCode() + "已存在");
        }
        SysConfig one = sysConfigRepository.save(sysConfig);
        if (Objects.nonNull(one)) {
            result.setSuccess(true);
            result.setMsg("保存配置成功");
        } else {
            result.setMsg("保存配置失败");
        }
        return result;
    }

    public RestMessage removeByCode(String code) {
        logger.info("删除配置信息");
        RestMessage result = new RestMessage();
        SysConfig old = findByCode(code);
        if (Objects.isNull(old)) {
            throw new BusinessException("该配置项不存在");
        }
        sysConfigRepository.delete(old.getId());
        result.setSuccess(true);
        result.setMsg("删除配置成功");
        return result;
    }

    public SysConfig findByCode(String code) {
        return sysConfigRepository.findByCode(code);
    }
}
