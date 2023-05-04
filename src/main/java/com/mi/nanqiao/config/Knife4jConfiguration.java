package com.mi.nanqiao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Knife4jConfiguration {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        String groupName = "3.X版本";
        Docket docket = new Docket(DocumentationType.OAS_30)
                .apiInfo(new ApiInfoBuilder()
                        .title("南乔到家接口文档")
                        .description("这里记录服务端所有的接口的入参，出参等等信息")
                        .termsOfServiceUrl("http://i.heyige.cn")
                        .contact(new Contact("mi", "http://i.heyige.cn", "3372563469@qq.com"))
                        .version("3.0")
                        .build()
                )
                //分组名称
                .groupName(groupName)
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.mi.nanqiao.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
