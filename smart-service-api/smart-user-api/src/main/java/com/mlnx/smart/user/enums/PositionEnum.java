package com.mlnx.smart.user.enums;

import com.mlnx.common.enums.BaseCodeEnum;

import lombok.Getter;

/**
 * Created by amanda.shan on 2019/3/26.
 */
@Getter
public enum PositionEnum implements BaseCodeEnum {
    ADMIN("00", "管理员"),

    DOCTOR("01", "医生"), Nurse("02", "护士");

    private String code;

    private String message;

    PositionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getValue() {
        return code;
    }

    @Override
    public String toString() {
        return "PositionEnum{" +
                "code='" + code + '\'' +
                '}';
    }
}
