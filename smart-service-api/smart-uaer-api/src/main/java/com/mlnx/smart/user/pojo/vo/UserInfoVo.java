package com.mlnx.smart.user.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mlnx.smart.user.enums.PositionEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by amanda.shan on 2019/3/26.
 */
@Data
@ApiModel("用户信息详情")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserInfoVo {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "职位")
    private PositionEnum position;

    @ApiModelProperty(value = "性别 0 男 1 女")
    private Integer sex;

}
