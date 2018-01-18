package com.xwbing.configuration;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.xwbing.handler.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明: servlet配置
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
@Configuration
public class DispatcherServletConfig extends WebMvcConfigurerAdapter {
    /***
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截器1:登录判断
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");
//        registry.addInterceptor(new LoginInterceptor())
//                .addPathPatterns("/**").excludePathPatterns("/user/login", "/servlet/captchaCode", "/swagger-ui.html");
        //拦截器2...
        super.addInterceptors(registry);
    }

    /**
     * 配置静态访问资源
     * 访问时不需要前缀
     * 默认:优先级
     * /**映射到
     * classpath:/META-INF/resources
     * classpath:/resources
     * classpath:/static
     * classpath:/public
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //swagger
//        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        //
        super.addResourceHandlers(registry);
    }

    /**
     * 视图配置
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login.html");
        registry.addRedirectViewController("doc", "swagger-ui.html");
        //添加更多
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }

    /**
     * 解决跨域
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
                .maxAge(3600);
    }

    /**
     * 扩展消息转换器，增加fastjson
     *
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(getFastJsonHttpMessageConverter());
        super.configureMessageConverters(converters);
    }

    /**
     * 请求处理程序映射适配器,使用fastJson
     *
     * @return
     */
    @Bean
    public HttpMessageConverter getFastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter httpMessageConverter = new FastJsonHttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        mediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        mediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        mediaTypes.add(MediaType.TEXT_HTML);//避免IE出现下载JSON文件的情况
        httpMessageConverter.setSupportedMediaTypes(mediaTypes);
        return httpMessageConverter;
    }
}
