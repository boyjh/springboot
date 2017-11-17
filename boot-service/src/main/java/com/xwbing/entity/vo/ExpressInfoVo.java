package com.xwbing.entity.vo;

import com.xwbing.entity.Trace;
import lombok.Data;

import java.util.List;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2017/11/17 9:20
 * 作者: xiangwb
 * 说明: 快递信息展示
 */
@Data
public class ExpressInfoVo {
    /**
     * 快递代码
     */
    private String ShipperCode;
    private Boolean Success;
    /**
     * 运单号
     */
    private String LogisticCode;
    /**
     * 状态
     */
    private String State;
    /**
     * 物流信息
     */
    private List<Trace> traces;
    /**
     * 描述
     */
    private String describe;
}
