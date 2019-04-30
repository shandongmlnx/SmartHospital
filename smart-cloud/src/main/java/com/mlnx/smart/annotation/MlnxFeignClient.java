package com.mlnx.smart.annotation;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by amanda.shan on 2019/3/29.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
//@FeignClient
public @interface MlnxFeignClient {

    @AliasFor("value")
    String name() default "";

    @AliasFor(annotation = FeignClient.class)
    String value() default "";

    @AliasFor(annotation = FeignClient.class)
    String serviceId() default "";

    @AliasFor(annotation = FeignClient.class)
    String url() default "";
}
