package com.xwbing.configuration;

import com.xwbing.util.captcha.CaptchaServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 说明: 统一的servlet配置
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
@Configuration
public class ServletConfig {
    private final Logger logger = LoggerFactory.getLogger(ServletConfig.class);

    @Bean
    public ServletRegistrationBean captchaServletRegistrationBean() {
        logger.info("注册验证码servlet ======================= ");
        CaptchaServlet captchaServlet = new CaptchaServlet();
        ServletRegistrationBean registration = new ServletRegistrationBean(captchaServlet);
        registration.setEnabled(true);
        registration.addUrlMappings("/servlet/captcha");
        return registration;
    }
    /********************************************/

}
