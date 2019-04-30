package com.mlnx.smart.device;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by amanda.shan on 2019/3/28.
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableFeignClients(basePackages = "com.mlnx.smart.user.feign")
@MapperScan("com.mlnx.smart.device.mapper")
@ComponentScan(basePackages = {"org.shan", "com.mlnx"})
public class SmartDeviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartDeviceApplication.class, args);
    }
}
