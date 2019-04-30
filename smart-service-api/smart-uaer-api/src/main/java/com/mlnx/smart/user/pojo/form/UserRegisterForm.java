package com.mlnx.smart.user.pojo.form;

import org.hibernate.validator.constraints.NotEmpty;

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
@ApiModel("用户用户名密码注册")
public class UserRegisterForm {

    @ApiModelProperty(value = "用户名")
    @NotEmpty(message="用户名不能为空")
    private String name;

    @ApiModelProperty(value = "密码")
    @NotEmpty(message="密码不能为空")
    private String password;

    public static void main(String[] args) {
        System.out.println(new UserRegisterForm().getName());
    }

}
