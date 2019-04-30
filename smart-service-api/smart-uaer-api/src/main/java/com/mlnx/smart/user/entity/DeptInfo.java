package com.mlnx.smart.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mlnx.common.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by amanda.shan on 2019/3/26.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("部门信息")
@TableName("t_user_dept")
public class DeptInfo extends BaseEntity {

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "位置")
    private String postion;
}
