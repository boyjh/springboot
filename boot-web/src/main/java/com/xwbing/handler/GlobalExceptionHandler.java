package com.xwbing.handler;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.exception.BusinessException;
import com.xwbing.util.JSONObjResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 说明:  GlobalExceptionHandler
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
// 作用在所有注解了@RequestMapping的控制器的方法上
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 自定义业务异常
     *
     * @param ex
     * @return
     */
    // 拦截处理控制器里对应的异常。
    @ExceptionHandler(value = BusinessException.class)
    // 返回给页面200状态码
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public JSONObject handlerGuideException(Exception ex) {
        logger.error(ex.getMessage());
        return JSONObjResult.toJSONObj(ex.getMessage());
    }

    /**
     * 表单检验(validator) 异常
     *
     * @param request
     * @param response
     * @param ex
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public JSONObject handlerBindException(HttpServletRequest request, HttpServletResponse response, BindException ex) {
        List<ObjectError> list = ex.getAllErrors();
        StringBuilder stringBuffer = new StringBuilder();
        for (ObjectError objectError : list) {
            if (stringBuffer.length() > 0)
                stringBuffer.append(" && ");
            stringBuffer.append(objectError.getDefaultMessage());
        }
        logger.error(stringBuffer.toString());
        response.setStatus(HttpStatus.OK.value());
        return JSONObjResult.toJSONObj(stringBuffer.toString());
    }

    /**
     * 表单检验(validator) 异常 (@RequestBody)
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public JSONObject handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        StringBuilder stringBuffer = new StringBuilder();
        for (ObjectError objectError : allErrors) {
            if (stringBuffer.length() > 0)
                stringBuffer.append(" && ");
            stringBuffer.append(objectError.getDefaultMessage());
        }
        logger.error(stringBuffer.toString());
        return JSONObjResult.toJSONObj(stringBuffer.toString());
    }

    /**
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public JSONObject handlerException(HttpServletRequest request, Exception ex) {
        logger.error(ex.getMessage());
        return JSONObjResult.toJSONObj("系统异常，请联系管理员");
    }
}
