package com.mlnx.common.form;


import javax.validation.constraints.Min;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by amanda.shan on 2019/3/26.
 */
@Data
@AllArgsConstructor
@ApiModel("分页查询的分页信息")
public class PageForm {

    @Min(value = 1, message = "页码不能小于1")
    @ApiModelProperty(value = "当前页，从1开始", required=true)
    private long current;

    @Min(value = 1, message = "每页的条数不能小于1")
    @ApiModelProperty(value = "每页的条数", required=true)
    private long size;
}
