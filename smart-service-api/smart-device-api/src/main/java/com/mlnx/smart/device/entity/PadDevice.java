package com.mlnx.smart.device.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Created by amanda.shan on 2019/3/28.
 */
@Data
@ApiModel("平板设备")
@TableName("t_device_pad")
public class PadDevice extends Device{
}
