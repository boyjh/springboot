package com.xwbing.constant;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 说明: 公共枚举
 * 项目名称: boot-module-demo
 * 创建日期: 2016年12月9日 上午9:50:30
 * 作者: xiangwb
 */
public class CommonEnum {
    private CommonEnum() {
    }

    public enum YesOrNoEnum {
        YES("是", "Y"), NO("否", "N");
        private String name;
        private String code;

        YesOrNoEnum(String name, String code) {
            this.name = name;
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    public enum SexEnum {
        MAN("男", "1"), WOMAN("女", "0");
        private String name;
        private String code;

        SexEnum(String name, String code) {
            this.name = name;
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) {
        String code = "Y";
//        for (YesOrNoEnum yesOrNoEnum : YesOrNoEnum.values()) {
//            if (yesOrNoEnum.getCode().equals(code)) {
//                System.out.println(yesOrNoEnum.getName());
//                break;
//            }
//        }
        YesOrNoEnum yesOrNoEnum = Arrays.stream(YesOrNoEnum.values()).filter(obj -> obj.getCode().equals(code)).findFirst().get();
        System.out.println(yesOrNoEnum.getName());

        List<JSONObject> resultVos = new ArrayList<>();
        JSONObject jsonObject;
        for (YesOrNoEnum status : YesOrNoEnum.values()) {
            jsonObject = new JSONObject();
            jsonObject.put("code", status.getCode());
            jsonObject.put("name", status.getName());
            resultVos.add(jsonObject);
        }
//        return resultVos;
    }
}
