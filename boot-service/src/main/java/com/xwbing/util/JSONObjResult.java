package com.xwbing.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

/**
 * 说明: 封装对象结果的json结果
 * 创建日期: 2016年12月14日 下午3:20:05
 * 作者: xiangwb
 */
@Data
public class JSONObjResult {
    /**
     * 是否成功
     */
    private boolean success;
    /***
     * 新增、修改主鍵返回id
     */
    private String id;

    /**
     * 消息体
     */
    private String message = "";
    /**
     * 返回数据
     */
    private Object data;

    public static JSONObject toJSONObj(Object o, boolean success, String message) {
        JSONObjResult jsonObjResult = new JSONObjResult();
        jsonObjResult.setSuccess(success);
        jsonObjResult.setMessage(message);
        jsonObjResult.setData(JSONUtil.beanToMap(o));
        return JSON.parseObject(JSON.toJSONString(jsonObjResult, SerializerFeature.WriteMapNullValue));
    }

    public static JSONObject toJSONObj(RestMessage rest) {
        JSONObjResult jsonObjResult = new JSONObjResult();
        jsonObjResult.setSuccess(rest.isSuccess());
        jsonObjResult.setMessage(rest.getMessage());
        jsonObjResult.setData(JSONUtil.beanToMap(rest.getData()));
        jsonObjResult.setId(rest.getId());
        return JSON.parseObject(JSON.toJSONString(jsonObjResult, SerializerFeature.WriteMapNullValue));
    }

    public static JSONObject toJSONObj(String error) {
        JSONObjResult jsonObjResult = new JSONObjResult();
        jsonObjResult.setSuccess(false);
        jsonObjResult.setMessage(error);
        return JSON.parseObject(JSONObject.toJSONString(jsonObjResult, SerializerFeature.WriteMapNullValue));
    }
}
