package com.mlnx.smart.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by amanda.shan on 2019/3/26.
 */
@SpringBootApplication(scanBasePackages = {"org.shan", "com.mlnx"})
@MapperScan("com.mlnx.smart.user.dao")
@RestController
public class SmartUerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartUerApplication.class, args);
    }

    @GetMapping("/")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }
}
