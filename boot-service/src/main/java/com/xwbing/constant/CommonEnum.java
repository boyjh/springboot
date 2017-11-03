package com.xwbing.constant;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 说明: 公共枚举
 * 项目名称: zdemo
 * 创建日期: 2016年12月9日 上午9:50:30
 * 作者: xiangwb
 */
public class CommonEnum {
    public enum YesOrNo {
        YES("是", "Y"), NO("否", "N");
        private String name;
        private String code;

        YesOrNo(String name, String code) {
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

    /**
     * 登录登出标记
     */
    public enum LoginInOut {
        IN("登录", 1), OUT("登出", 2);
        private String name;
        private int value;

        LoginInOut(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    public enum ValidateResultEnum {
        SUCCESS("校验通过", 1), FAILED("校验失败", 0);
        private String msg;
        private int errorCode;

        ValidateResultEnum(String msg, int errorCode) {
            this.msg = msg;
            this.errorCode = errorCode;
        }

        public String getMsg() {
            return this.msg;
        }

        public int getErrorCode() {
            return this.errorCode;
        }
    }

    public static void main(String[] args) {
        String code = "Y";
        for (YesOrNo yesOrNo : YesOrNo.values()) {
            if (yesOrNo.getCode().equals(code)) {
                System.out.println(yesOrNo.getName());
                break;
            }
        }
        YesOrNo yesOrNo = Arrays.stream(YesOrNo.values()).filter(obj -> obj.getCode().equals(code)).collect(Collectors.toList()).get(0);
        System.out.println(yesOrNo.getName());
    }
}
