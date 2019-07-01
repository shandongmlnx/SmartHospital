package com.mlnx.smart.common.enums;

/**
 * Created by amanda.shan on 2019/7/1.
 */
public enum ResponseEnum {
    SUCESS("0000", "成功"),

    LOGIN_FAIL("1000", "登录失败"),
    USER_FAIL("2000", "用户失败"),

    ;


    private String code;

    private String describe;

    ResponseEnum(String code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
