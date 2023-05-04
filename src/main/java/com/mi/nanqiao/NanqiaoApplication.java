package com.mi.nanqiao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan(basePackages = "com.mi.nanqiao.dao")
public class NanqiaoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(NanqiaoApplication.class, args);
        System.out.println(context.getApplicationName());
    }

}
