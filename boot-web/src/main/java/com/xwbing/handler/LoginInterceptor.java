package com.xwbing.handler;

import com.alibaba.fastjson.JSON;
import com.xwbing.configuration.DispatcherServletConfig;
import com.xwbing.constant.CommonConstant;
import com.xwbing.util.CommonDataUtil;
import com.xwbing.util.RestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
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
        //映射到登录页面，不拦截
        set.add("/");
        //映射swagger文档
        set.add("/doc");
        //验证码
        set.add("/captcha");
        //swagger
        set.add("/v2/api-docs");
        set.add("/swagger-resources");
        set.add("/configuration/ui");
        set.add("/configuration/security");
        //德鲁伊监控
        set.add("/druid");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();
        if (!set.contains(servletPath) && !servletPath.contains("login") && !servletPath.contains("test")) {
//            HttpSession session = request.getSession();
//            if (session.getAttribute(CommonConstant.CURRENT_USER)!=null) {
//                return true;
//            }else {
//                return false;
//            }
            if (CommonDataUtil.getToken(CommonConstant.CURRENT_USER) != null) {
                return true;
            } else {
                logger.error("用户未登录");
                OutputStream outputStream = response.getOutputStream();
                RestMessage restMessage = new RestMessage();
                restMessage.setMessage("请先登录");
                outputStream.write(JSON.toJSONString(restMessage).getBytes("utf-8"));
                outputStream.close();
                //未登录，重定向到登录页面
//                response.sendRedirect("/login.html");
                return false;
            }
        }
        return true;
    }
}
