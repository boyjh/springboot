package com.xwbing.handler;

import com.xwbing.annotation.Idempotent;
import com.xwbing.constant.CommonConstant;
import com.xwbing.util.CommonDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author xiangwb
 * @date 2018/8/27 23:10
 * @description 接口幂等切面
 */
@Slf4j
@Aspect
@Component
public class IdempotentAspect {
    //名称限定表达式
    @Pointcut("execution(public * com.xwbing.controller.*.*(..)) && @annotation(idempotent)")
    public void pointCut(Idempotent idempotent) {
    }

    @Pointcut("within(com.xwbing.controller..*) && @annotation(idempotent)")
    public void pointCutWithMsg(Idempotent idempotent) {
    }

    //环绕通知如果return null有拦截的效果
    @Around(value = "pointCutWithMsg(idempotent)", argNames = "pjp,idempotent")
    public Object idempotent(ProceedingJoinPoint pjp, Idempotent idempotent) throws Throwable {
        String type = idempotent.type();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String sign;
        if (CommonConstant.HEADER.equals(type)) {
            sign = request.getHeader("sign");
        } else {
            sign = request.getParameter("sign");
        }
        if (sign == null || sign.length() == 0) {
            response("sign不能为空");
            return null;
        }
        String cache = (String) CommonDataUtil.getData(sign);
        if (cache != null && cache.equals(sign)) {
            CommonDataUtil.clearData(sign);
        } else {
            response("请勿重复提交");
            return null;
        }
        return pjp.proceed();
    }

    private void response(String msg) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        response.setHeader("Content-Type", "text/html;charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.println(msg);
        } catch (IOException e) {
            log.error("");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
