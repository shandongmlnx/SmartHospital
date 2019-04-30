package com.mlnx.smart.device.pojo.vo;

import com.mlnx.smart.device.entity.PadDevice;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by amanda.shan on 2019/3/28.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PadDeviceVo extends PadDevice {

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "位置")
    private String postion;

}
