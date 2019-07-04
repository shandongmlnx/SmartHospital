package com.mlnx.smart.user.config;

import org.shan.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * Created by amanda.shan on 2019/6/29.
 */
@Configuration
public class UserAuthorizeConfig {

    @Bean
    public AuthorizeConfigProvider authorizeConfigProvider() {
        return new AuthorizeConfigProvider() {

            @Override
            public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
                registry.antMatchers("/user/**")
                        .access("@rbacService.hasPermission(request,authentication)");

                return true;
            }
        };
    }

}
