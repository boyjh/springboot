package com.xwbing.util.captcha;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/3/28 14:51
 * 作者: xiangwb
 * 说明:
 */
public class CaptchaException extends RuntimeException {
    private static final long serialVersionUID = 3106485920407116428L;

    public CaptchaException(Throwable cause) {
        super(cause);
    }

    public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException(String message, Throwable cause) {
        super(message, cause);
    }
}
