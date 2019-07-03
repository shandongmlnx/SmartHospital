package com.mlnx.smart.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.FileNotFoundException;

/**
 * Created by amanda.shan on 2019/3/26.
 */
@SpringBootApplication(scanBasePackages = {"org.shan", "com.mlnx"})
@MapperScan("com.mlnx.smart.user.dao")
@EnableTransactionManagement
public class SmartUerApplication {


    public static void main(String[] args) throws FileNotFoundException {

        SpringApplication.run(SmartUerApplication.class, args);
    }
}
