package com.mlnx.smart.area.config;


import org.shan.security.core.authorize.AuthorizeConfigProvider;
import org.shan.security.core.exception.BadRequestAccessDeniedException;
import org.shan.security.core.role.RbacService;
import org.shan.security.core.role.iml.RbacServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
                registry.antMatchers("/area/**")
                        .access("@rbacService.hasPermission(request,authentication)");

                return true;
            }
        };
    }

    @Bean("rbacService")
    public RbacService rbacService() {
        return new RbacServiceImpl();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {

        AccessDeniedHandlerImpl deniedHandler = new AccessDeniedHandlerImpl();

        return new AccessDeniedHandler() {


            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response,
                               AccessDeniedException accessDeniedException) throws IOException, ServletException {

                if (accessDeniedException instanceof BadRequestAccessDeniedException){
                    if (!response.isCommitted()) {
                        response.sendError(HttpStatus.BAD_REQUEST.value(),
                                accessDeniedException.getMessage());
                    }
                }else{
                    deniedHandler.handle(request, response, accessDeniedException);
                }


            }
        };
    }

}
