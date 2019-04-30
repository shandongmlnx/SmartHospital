/*
 * ====================================================================
 * 【个人网站】：http://www.2b2b92b.com
 * 【网站源码】：http://git.oschina.net/zhoubang85/zb
 * 【技术论坛】：http://www.2b2b92b.cn
 * 【开源中国】：https://gitee.com/zhoubang85
 *
 * 【支付-微信_支付宝_银联】技术QQ群：470414533
 * 【联系QQ】：842324724
 * 【联系Email】：842324724@qq.com
 * ====================================================================
 */
package com.mlnx.common.entity;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mlnx.common.enums.PublicStatusEnum;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 基类
 *
 * @author zhoubang
 * @date 2017年10月18日 13:41:20
 */
@Data
@Accessors(chain = true)
public class BaseEntity implements Serializable{

    private static final long serialVersionUID = -8151372638558119411L;

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键id")
    private Integer id;

    @JsonIgnore
    @EnumValue
    @TableField(exist=false)
    @ApiModelProperty(value = "是否已删除")
    private PublicStatusEnum status;// 状态 默认数据库定义啦这个字段

    @JsonIgnore
    @TableField(exist=false)
    @ApiModelProperty(value = "创建人")
    private String creater;// 创建人.

    @JsonIgnore
    @TableField(exist=false)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;// 创建时间.

    @JsonIgnore
    @TableField(exist=false)
    @ApiModelProperty(value = "更新人")
    private String editor;// 修改人.

    @ApiModelProperty(value = "更新时间")
    @JsonIgnore
    @TableField(exist=false, update="now()")
    private Date editTime = new Date();// 修改时间.

    @JsonIgnore
    @TableField(exist=false)
    @ApiModelProperty(value = "描述")
    private String remark;// 描述

}
