package com.xwbing.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明: swagger配置
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket sysDocket() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        ticketPar.name("token").description("令牌")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的ticket参数非必填，传空也可以
        pars.add(ticketPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("system")
                .apiInfo(sysApiInf())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xwbing.controller.sys"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo sysApiInf() {
        return new ApiInfoBuilder()
                .title("RESTful API Document")
                .description("系统接口文档")
                .termsOfServiceUrl("http://localhost:8080/swagger-ui.html")
                .contact(new Contact("项伟兵", "https://github.com/xiangwbs/boot-module-pro.git", "xiangwbs@163.com"))
                .version("1.0.0")
                .build();
    }

    @Bean
    public Docket otherDocket() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        ticketPar.name("token").description("令牌")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的ticket参数非必填，传空也可以
        pars.add(ticketPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("other")
                .apiInfo(otherApiInf())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xwbing.controller.other"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo otherApiInf() {
        return new ApiInfoBuilder()
                .title("RESTful API Document")
                .description("other接口文档")
                .termsOfServiceUrl("http://localhost:8080/swagger-ui.html")
                .contact(new Contact("项伟兵", "https://github.com/xiangwbs/boot-module-pro.git", "xiangwbs@163.com"))
                .version("1.0.0")
                .build();
    }
}
