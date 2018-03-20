package com.xwbing.shiro;

import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

@Data
public class UsernamePasswordCaptchaToken extends UsernamePasswordToken {
    private static final long serialVersionUID = 6059396037971862504L;
    private String captcha;

    public UsernamePasswordCaptchaToken() {
        super();

    }
    public UsernamePasswordCaptchaToken(String username, char[] password, boolean rememberMe, String host, String captcha) {
        super(username, password, rememberMe, host);
        this.captcha = captcha;
    }
}
