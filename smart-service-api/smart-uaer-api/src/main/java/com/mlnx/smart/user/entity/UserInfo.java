package com.mlnx.smart.user.entity;

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
@TableName("t_user_info")
public class UserInfo extends BaseEntity {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "职位")
    private PositionEnum position;

    @ApiModelProperty(value = "性别 0 男 1 女")
    private Integer sex;

    @ApiModelProperty(value = "区域Id")
    private Integer deptId;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "工号")
    private String workId;


}
