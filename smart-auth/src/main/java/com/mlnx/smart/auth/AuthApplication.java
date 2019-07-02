package com.mlnx.smart.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by amanda.shan on 2019/6/3.
 */
@SpringBootApplication(scanBasePackages = {"org.shan", "com.mlnx"})
@MapperScan("com.mlnx.smart.auth.mapper")
@RestController
public class AuthApplication {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @GetMapping("")
    public String test() {
        return "test";
    }

}
