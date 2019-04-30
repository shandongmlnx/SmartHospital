package com.mlnx.common.annotations;

/**
 **/
@NeedLogin
public @interface NeedPermission {

    String[] permissions() default {};

}
