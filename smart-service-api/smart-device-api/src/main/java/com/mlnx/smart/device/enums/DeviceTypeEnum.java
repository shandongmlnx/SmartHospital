package com.mlnx.smart.device.enums;

import com.mlnx.common.enums.BaseCodeEnum;

/**
 * Created by amanda.shan on 2019/3/29.
 */
public enum DeviceTypeEnum implements BaseCodeEnum {

    PAD("00", "平板"), AGV_CAR("01", "AGV小车");

    private String code;

    private String message;

    DeviceTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getValue() {
        return code;
    }
}
