package com.mlnx.smart.user.pojo.form;

import com.mlnx.common.form.PageForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by amanda.shan on 2019/6/27.
 */
@Data
@ApiModel("用户查询筛选类")
public class UserFilterForm extends PageForm {

    @ApiModelProperty(value = "手机号")
    private String mobile;

}
