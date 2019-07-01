package com.mlnx.smart.user.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mlnx.common.entity.BaseEntity;
import com.mlnx.smart.user.enums.PositionEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by amanda.shan on 2019/3/26.
 */
@Data
@ApiModel("用户信息")
@TableName("t_user")
public class UserInfo extends BaseEntity {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "职位")
    @EnumValue
    private PositionEnum position;

    @ApiModelProperty(value = "工号")
    private String card;

    @ApiModelProperty(value = "指纹1")
    private byte[] finger1;

    @ApiModelProperty(value = "指纹2")
    private byte[] finger2;

    @ApiModelProperty(value = "指纹3")
    private byte[] finger3;

    @ApiModelProperty(value = "人脸")
    private byte[] face;


}
