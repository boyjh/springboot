package com.xwbing.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/3/28 14:06
 * 作者: xiangwb
 * 说明: anon,authc
 */
@Configuration
@PropertySource("classpath:shiro.properties")
public class ShiroConfig {
    @Value("${rememberMeCookieMaxAge}")
    private Integer rememberMeCookieMaxAge;
    @Value("${globalSessionTimeout}")
    private Long globalSessionTimeout;
    @Value("${sessionValidationInterval}")
    private String sessionValidationInterval;
    @Value("${sessionsCacheName}")
    private String sessionsCacheName;
    @Value("${sessionIdCookieName}")
    private String sessionIdCookieName;

    @Bean
    public ShiroFilterFactoryBean shirFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //配置访问权限
        Map<String, String> chains = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断
        //配置登录的url和登录成功的url
        shiroFilterFactoryBean.setLoginUrl("/user/login");
//        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
//        chains.put("/favicon.ico", "anon");
//        chains.put("/static/**", "anon");
//        chains.put("/", "anon");
//        chains.put("/doc", "anon");
//        chains.put("/captcha", "anon");
//        chains.put("/v2/api-docs", "anon");
//        chains.put("/swagger-resources", "anon");
//        chains.put("/configuration/ui", "anon");
//        chains.put("/configuration/security", "anon");
//        chains.put("/druid", "anon");
//        chains.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(chains);
        return shiroFilterFactoryBean;
    }

    @Bean
    public MyShiroRealm shiroRealm() {
        return new MyShiroRealm();
    }


    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }
}
