package com.xwbing.service.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xwbing.constant.CommonEnum;
import com.xwbing.domain.entity.sys.SysUserLoginInOut;
import com.xwbing.domain.mapper.sys.SysUserLoginInOutMapper;
import com.xwbing.util.DateUtil2;
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
import java.util.stream.Collectors;

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
    public Pagination page(Integer inout, String startDate, String endDate, Pagination page) {
        Map<String, Object> map = new HashMap<>();
        if (inout != null) {
            map.put("inout", inout);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            map.put("startDate", startDate + " 00:00:00");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            map.put("endDate", endDate + " 23:59:59");
        }
        PageInfo<SysUserLoginInOut> pageInfo = PageHelper.startPage(page.getCurrentPage(), page.getPageSize()).doSelectPageInfo(() -> loginInOutMapper.findByInoutType(map));
        List<SysUserLoginInOut> list = pageInfo.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(loginInOut -> {
                //登录登出
                CommonEnum.LoginInOutEnum inOutEnum = Arrays.stream(CommonEnum.LoginInOutEnum.values()).filter(obj -> obj.getValue() == loginInOut.getInoutType()).findFirst().get();
                loginInOut.setInoutTypeName(inOutEnum.getName());
            });
        }
        return page.result(page, pageInfo);
    }

    /**
     * 登录登出饼图
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONArray pie(String startDate, String endDate) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotEmpty(startDate)) {
            map.put("startDate", startDate + " 00:00:00");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            map.put("endDate", endDate + " 23:59:59");
        }
        List<SysUserLoginInOut> list = loginInOutMapper.countByType(map);
        int in = 0;
        int out = 0;
        for (SysUserLoginInOut inOut : list) {
            if (inOut.getInoutType() == 1) {
                in = inOut.getCount();
            } else {
                out = inOut.getCount();
            }
        }
        JSONArray result = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("name", "in");
        obj.put("value", in);
        result.add(obj);
        obj = new JSONObject();
        obj.put("name", out);
        obj.put("value", out);
        return result;
    }

    /**
     * 登录登出柱状图
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public Map<String, Object> bar(String startDate, String endDate) {
        Map<String, Object> result = new HashMap<>();
        //统计时间
        List<String> days = DateUtil2.listDate(startDate, endDate);
        result.put("xAxis", days);
        //统计数据
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotEmpty(startDate)) {
            map.put("startDate", startDate + " 00:00:00");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            map.put("endDate", endDate + " 23:59:59");
        }
        List<SysUserLoginInOut> list = loginInOutMapper.findByInoutType(map);
        JSONArray in = new JSONArray();
        JSONArray out = new JSONArray();
        if (CollectionUtils.isNotEmpty(list)) {
            Map<String, List<SysUserLoginInOut>> collect = list.stream().peek(obj -> obj.setRecordTime(obj.getRecordTime().substring(0, 10))).collect(Collectors.groupingBy(SysUserLoginInOut::getRecordTime));
            days.forEach(day -> {
                List<SysUserLoginInOut> sample = collect.get(day);
                if (CollectionUtils.isNotEmpty(sample)) {
                    int sumIn = sample.stream().filter(obj -> obj.getInoutType() == 1).mapToInt(SysUserLoginInOut::getCount).sum();
                    int sumOut = sample.stream().filter(obj -> obj.getInoutType() == 2).mapToInt(SysUserLoginInOut::getCount).sum();
                    in.add(sumIn);
                    out.add(sumOut);
                } else {
                    in.add(0);
                    out.add(0);
                }
            });

        } else {
            days.forEach(day -> {
                in.add(0);
                out.add(0);
            });
        }
        JSONArray series = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("name", "in");
        obj.put("data", in);
        series.add(obj);
        obj = new JSONObject();
        obj.put("name", "out");
        obj.put("data", out);
        series.add(obj);
        result.put("series", series);
        return result;
    }

}
