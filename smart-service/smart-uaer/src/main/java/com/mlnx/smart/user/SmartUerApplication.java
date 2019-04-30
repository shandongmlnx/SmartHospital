package com.mlnx.smart.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by amanda.shan on 2019/3/26.
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.mlnx.smart.user.mapper")
@ComponentScan(basePackages = {"org.shan", "com.mlnx"})
public class SmartUerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartUerApplication.class, args);
    }
}
