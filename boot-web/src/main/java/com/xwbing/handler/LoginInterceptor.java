package com.xwbing.handler;

import com.xwbing.configuration.DispatcherServletConfig;
import com.xwbing.constant.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

/**
 * 说明:  登录拦截器
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private final Logger logger = LoggerFactory.getLogger(DispatcherServletConfig.class);
    private static final Set<String> set = new HashSet<>();//拦截器白名单
    static {
        set.add("/v2/api-docs");
        set.add("/servlet/captchaCode");
        set.add("/swagger-ui.html");
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();
        if(!set.contains(servletPath) && !servletPath.contains("login")){
            HttpSession session = request.getSession();
            if (session.getAttribute(CommonConstant.SESSION_CURRENT_USER)!=null) {
                // TODO: 2017/10/3
                return true;
            }else {
                // TODO: 2017/10/3
                return false;
            }
        }
        return true;
    }
}
