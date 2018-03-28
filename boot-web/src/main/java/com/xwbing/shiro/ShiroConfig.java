package com.xwbing.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/3/28 14:06
 * 作者: xiangwb
 * 说明: anon,authc
 */
@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //配置登录的url和登录成功的url
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        //配置访问权限
        Map<String, String> chains = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断
        chains.put("/favicon.ico", "anon");
        chains.put("/static/**", "anon");
        //映射到登录页面，不拦截
        chains.put("/","anon");
        //映射swagger文档
        chains.put("/doc","anon");
        //验证码
        chains.put("/captcha","anon");
        //swagger
        chains.put("/v2/api-docs","anon");
        chains.put("/swagger-resources","anon");
        chains.put("/configuration/ui","anon");
        chains.put("/configuration/security","anon");
        //德鲁伊监控
        chains.put("/druid","anon");
        chains.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(chains);
        System.out.println("Shiro拦截器工厂类注入成功");
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
