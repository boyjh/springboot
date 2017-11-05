package com.xwbing.configuration;

import com.xwbing.util.captcah.CaptchaServlet;
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
    @Bean
    public CaptchaServlet captchaServlet() {
        return new CaptchaServlet();
    }

    @Bean
    public ServletRegistrationBean captchaServletRegistrationBean(CaptchaServlet captchaServlet) {
        ServletRegistrationBean registration = new ServletRegistrationBean(captchaServlet);
        registration.setEnabled(true);
        registration.addUrlMappings("/servlet/captchaCode");
        return registration;
    }
    /********************************************/

}
