package com.mlnx.smart.device.entity;

import com.mlnx.common.entity.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by amanda.shan on 2019/3/28.
 */
@Data
public class Device extends BaseEntity {

    @ApiModelProperty(value = "设备类型")
    private String deviceType;

    @ApiModelProperty(value = "部门id")
    private Integer deptId;

    @ApiModelProperty(value = "其他信息")
    private Integer otherMsg;
}
