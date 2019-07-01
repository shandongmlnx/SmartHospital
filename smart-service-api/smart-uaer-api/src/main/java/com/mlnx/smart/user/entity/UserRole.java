package com.mlnx.smart.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mlnx.common.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Created by amanda.shan on 2019/7/1.
 */
@Data
@ApiModel("用户角色")
@TableName("t_user_role")
public class UserRole extends BaseEntity {

    public UserRole() {
    }

    public UserRole(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    private Integer userId;

    private Integer roleId;
}
