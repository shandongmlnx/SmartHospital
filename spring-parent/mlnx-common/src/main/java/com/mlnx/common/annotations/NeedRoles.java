package com.mlnx.common.annotations;

@NeedLogin
public @interface NeedRoles {

    String[] roles() default {};

}
