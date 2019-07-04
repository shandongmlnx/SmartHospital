package com.mlnx.smart.service.config;

import org.shan.security.core.exception.BadRequestAccessDeniedException;
import org.shan.security.core.role.RbacService;
import org.shan.security.core.role.iml.RbacServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by amanda.shan on 2019/7/3.
 */
@Configuration
public class AuthorizeConfig {

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

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.addExposedHeader("head1");
        //corsConfiguration.addExposedHeader("Location");
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
