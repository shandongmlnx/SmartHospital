package com.mlnx.smart.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mlnx.common.entity.Response;
import com.mlnx.common.entity.ResponseData;
import com.mlnx.common.form.PageForm;
import com.mlnx.smart.user.pojo.form.UserRegisterForm;
import com.mlnx.smart.user.pojo.vo.UserInfoVo;
import com.mlnx.smart.user.service.IUserService;

import org.shan.spring.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by amanda.shan on 2019/3/26.
 */
@Api("用户接口")
@RestController
@RequestMapping("/uer")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value="用户账号密码注册", notes="")
    @PostMapping("/register")
    public Response register(@Valid UserRegisterForm userRegisterForm){
        userService.register(userRegisterForm);

        return result();
    }

    @ApiOperation(value="分页获取用户列表")
    @GetMapping()
    public ResponseData<IPage<UserInfoVo>> register(@Valid PageForm pageForm){
        return result(userService.listUserInfo(pageForm));
    }

}
