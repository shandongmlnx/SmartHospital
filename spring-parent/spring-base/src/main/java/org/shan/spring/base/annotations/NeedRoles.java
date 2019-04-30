package org.shan.spring.base.annotations;

@NeedLogin
public @interface NeedRoles {

    String[] roles() default {};

}
