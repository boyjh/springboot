package com.xwbing.handler;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author xiangwb
 * @date 20/2/1 15:51
 * spring content
 */
@Configuration
public class ApplicationContextHelper implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHelper.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBeansWithAnnotation(Class<T> clazz) {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation((Class<? extends Annotation>) clazz);
        return (T) applicationContext.getBeansOfType(clazz);
    }
}
