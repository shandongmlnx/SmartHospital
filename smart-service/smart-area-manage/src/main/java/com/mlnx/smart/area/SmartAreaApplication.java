package com.mlnx.smart.area;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by zzb on 2019/7/1.
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.mlnx.smart.area.mapper")
@ComponentScan(basePackages = {"org.shan", "com.mlnx"})
public class SmartAreaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartAreaApplication.class, args);
    }
}
