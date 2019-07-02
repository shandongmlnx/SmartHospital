package com.mlnx.smart.auth.config;

import com.mlnx.common.entity.Response;
import com.mlnx.smart.auth.exception.LoginException;
import com.mlnx.smart.common.enums.ResponseEnum;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Created by amanda.shan on 2019/7/1.
 */
@Configuration
public class AuthConfig {

    @Bean
    public MlnxWebResponseExceptionTranslator translator() {
        return new MlnxWebResponseExceptionTranslator();
    }

    // 解析接口请求异常的处理类 下面只处理登入异常的情况
    static class MlnxWebResponseExceptionTranslator extends DefaultWebResponseExceptionTranslator {

        @Override
        public ResponseEntity translate(Exception e) throws Exception {

            if (e instanceof LoginException) {
                LoginException loginException = (LoginException) e;

                HttpHeaders headers = new HttpHeaders();

                ResponseEntity response =
                        new ResponseEntity(new Response(ResponseEnum.LOGIN_FAIL.getCode(), e.getMessage()),
                                headers, HttpStatus.FORBIDDEN);

                return response;

            } else {
                return super.translate(e);
            }
        }

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
