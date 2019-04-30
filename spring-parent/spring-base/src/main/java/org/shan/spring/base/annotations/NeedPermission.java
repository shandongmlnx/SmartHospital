package org.shan.spring.base.annotations;

/**
 **/
@NeedLogin
public @interface NeedPermission {

    String[] permissions() default {};

}
