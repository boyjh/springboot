package com.xwbing.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 说明: 封装对象结果的json结果
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
    private String message = "操作成功";
    /**
     * 返回数据
     */
    private Object data;

    /**
     * 查询返回值为Object
     * @param o
     * @param message
     * @return
     */
    public static JSONObject toJSONObj(Object o, String message) {
        JSONObjResult jsonObjResult = new JSONObjResult();
        jsonObjResult.setSuccess(true);
        if (StringUtils.isNotEmpty(message)) {
            jsonObjResult.setMessage(message);
        }
        jsonObjResult.setData(JSONUtil.beanToMap(o));
        return JSON.parseObject(JSON.toJSONString(jsonObjResult, SerializerFeature.WriteMapNullValue));
    }

    /**
     * 增删改返回值为RestMessage
     * @param rest
     * @return
     */
    public static JSONObject toJSONObj(RestMessage rest) {
        JSONObjResult jsonObjResult = new JSONObjResult();
        jsonObjResult.setSuccess(rest.isSuccess());
        jsonObjResult.setMessage(rest.getMessage());
        jsonObjResult.setData(JSONUtil.beanToMap(rest.getData()));
        jsonObjResult.setId(rest.getId());
        return JSON.parseObject(JSON.toJSONString(jsonObjResult, SerializerFeature.WriteMapNullValue));
    }

    /**
     * 直接返回错误提示
     * @param error
     * @return
     */
    public static JSONObject toJSONObj(String error) {
        JSONObjResult jsonObjResult = new JSONObjResult();
        jsonObjResult.setSuccess(false);
        jsonObjResult.setMessage(error);
        return JSON.parseObject(JSONObject.toJSONString(jsonObjResult, SerializerFeature.WriteMapNullValue));
    }
}
