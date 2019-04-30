package com.mlnx.smart.user.pojo.form;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by amanda.shan on 2019/3/27.
 */
@Data
@ApiModel("部门信息提交")
public class DeptInfoForm {

    private Integer id;

    @NotEmpty(message = "部门名称不能为空")
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @NotEmpty(message = "部门位置不能为空")
    @ApiModelProperty(value = "位置")
    private String postion;
}
