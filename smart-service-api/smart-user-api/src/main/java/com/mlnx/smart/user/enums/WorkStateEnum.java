package com.mlnx.smart.user.enums;

import com.mlnx.common.enums.BaseCodeEnum;

import lombok.Getter;

/**
 * Created by amanda.shan on 2019/7/3.
 */
@Getter
public enum WorkStateEnum implements BaseCodeEnum  {

    ONLINE("00", "在岗"), OFFLINE("01", "离岗");

    private String code;

    private String message;

    WorkStateEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getValue() {
        return code;
    }

    @Override
    public String toString() {
        return "WorkStateEnum{" +
                "code='" + code + '\'' +
                '}';
    }
}