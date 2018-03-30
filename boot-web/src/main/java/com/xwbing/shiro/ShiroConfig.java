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
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //配置访问权限
        Map<String, String> chains = new LinkedHashMap<>();
        shiroFilterFactoryBean.setLoginUrl("/user/login");
        shiroFilterFactoryBean.setSuccessUrl("/doc");
        //静态资源
        chains.put("/META-INF/resources/**", "anon");
        chains.put("/resources/**", "anon");
        chains.put("/static/**", "anon");
        chains.put("/public/**", "anon");
        chains.put("/*.*", "anon");
        //验证码
        chains.put("/captcha", "anon");
        //swagger
        chains.put("/webjars/**", "anon");
        chains.put("/v2/api-docs", "anon");
        chains.put("/swagger-resources", "anon");
        chains.put("/configuration/ui", "anon");
        chains.put("/configuration/security", "anon");
        chains.put("/doc", "anon");
        //德鲁伊数据源
        chains.put("/druid/*", "anon");
        chains.put("/druid", "anon");
        //权限认证路径
        chains.put("/**", "authc");
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
