package com.mlnx.smart.auth.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.mlnx.common.entity.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by amanda.shan on 2019/6/20.
 */
@Data
@TableName("t_user")
public class UserInfo extends BaseEntity {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "性别 0 男 1 女")
    private Integer sex;

    @ApiModelProperty(value = "区域Id")
    private Integer deptId;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "工号")
    private String card;


}
