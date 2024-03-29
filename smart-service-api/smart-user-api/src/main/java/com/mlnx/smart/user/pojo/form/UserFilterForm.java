package com.mlnx.smart.user.pojo.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by amanda.shan on 2019/6/27.
 */
@Data
@ApiModel("用户查询筛选类")
public class UserFilterForm {

    @ApiModelProperty(value = "手机号")
    private String mobile;

}
