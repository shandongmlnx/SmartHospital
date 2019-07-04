package com.mlnx.smart.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mlnx.common.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Created by amanda.shan on 2019/7/1.
 */
@Data
@ApiModel("角色")
@TableName("t_role")
public class Role extends BaseEntity {

    private String roleName;

    private String roleKey;
}
