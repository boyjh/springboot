package com.xwbing.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/3/29 15:28
 * 作者: xiangwb
 * 说明:
 */
public class FormAuthenticationCaptchaFilter extends FormAuthenticationFilter {
    public static final String DEFAULT_CAPTCHA_PARAM = "captcha_code";
    private String captchaParam = DEFAULT_CAPTCHA_PARAM;

    public String getCaptchaParam() {
        return captchaParam;
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());

    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptcha(request);
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        return new UsernamePasswordCaptchaToken(username, password.toCharArray(), rememberMe, host, captcha);
    }
}
