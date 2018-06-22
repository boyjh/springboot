package com.xwbing.service.sys;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xwbing.constant.CommonEnum;
import com.xwbing.domain.entity.sys.SysUserLoginInOut;
import com.xwbing.domain.mapper.sys.SysUserLoginInOutMapper;
import com.xwbing.util.Pagination;
import com.xwbing.util.RestMessage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2017/11/7 9:56
 * 作者: xiangwb
 * 说明: 用户登录登出服务层
 */
@Service
public class SysUserLoginInOutService {
    @Resource
    private SysUserLoginInOutMapper loginInOutMapper;

    /**
     * 保存
     *
     * @param inOut
     * @return
     */
    public RestMessage save(SysUserLoginInOut inOut) {
        RestMessage result = new RestMessage();
        int save = loginInOutMapper.insert(inOut);
        if (save == 1) {
            result.setSuccess(true);
            result.setMessage("保存登录登出信息成功");
        } else {
            result.setMessage("保存登录登出信息失败");
        }
        return result;
    }

    /**
     * 根据类型分页查询
     *
     * @param inout
     * @return
     */
    public Pagination pageByType(int inout, String startDate, String endDate, Pagination page) {
        Map<String, Object> map = new HashMap<>();
        map.put("inout", inout);
        if (StringUtils.isNotEmpty(startDate)) {
            map.put("startDate", startDate + " 00:00:00");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            map.put("endDate", endDate + " 23:59:59");
        }
        PageInfo<SysUserLoginInOut> pageInfo = PageHelper.startPage(page.getCurrentPage(), page.getPageSize()).doSelectPageInfo(() -> loginInOutMapper.findByInoutType(map));
        List<SysUserLoginInOut> list = pageInfo.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(inOut -> {
                //登录登出
                CommonEnum.LoginInOutEnum inOutEnum = Arrays.stream(CommonEnum.LoginInOutEnum.values()).filter(obj -> obj.getValue() == inout).findFirst().get();
                inOut.setInoutTypeName(inOutEnum.getName());
            });
        }
        return page.result(page, pageInfo);
    }
}
