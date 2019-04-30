package com.mlnx.smart.device.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;

/**
 * Created by amanda.shan on 2019/3/29.
 */
@Configuration
public class DeviceConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
