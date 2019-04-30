package org.shan.spring.base.config;

import org.shan.spring.base.utils.converter.StringToDateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;

/**
 * Created by amanda.shan on 2018/10/31.
 */
@Configuration
public class WebConfigBeans implements WebMvcConfigurer {

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @Override
    public void addFormatters(FormatterRegistry registry) {

        DefaultFormattingConversionService conversionService = (DefaultFormattingConversionService) registry;

        // 增加字符串转日期的功能
        conversionService.addConverter(new StringToDateConverter());
    }
}
