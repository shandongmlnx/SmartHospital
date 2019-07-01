package com.mlnx.smart.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mlnx.common.annotations.NeedLogin;
import com.mlnx.common.entity.Response;
import com.mlnx.common.entity.ResponseData;
import com.mlnx.common.form.PageForm;
import com.mlnx.smart.user.entity.UserInfo;
import com.mlnx.smart.user.pojo.form.UserFilterForm;
import com.mlnx.smart.user.service.UserService;

import org.shan.spring.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/user/")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @NeedLogin(permissions = {"User/C"})
    @ApiOperation(value="用户账号注册", notes="")
    @PostMapping()
    public Response register(@RequestBody UserInfo userInfo){
        userService.register(userInfo);

        return result();
    }

    @NeedLogin(permissions = {"User/U"})
    @ApiOperation(value="用户更新", notes="")
    @PutMapping("{id}")
    public Response modify(@Valid @RequestBody UserInfo userInfo, @PathVariable("id") Integer id ){

        userInfo.setId(id);
        userService.modify(userInfo);

        return result();
    }

    @NeedLogin(permissions = {"User/S"})
    @ApiOperation(value="获取所有用户信息", notes="")
    @GetMapping()
    public ResponseData list(@Valid UserFilterForm userFilterForm){

        ResponseData result = result(userService.list(userFilterForm));
        return result;
    }

    @NeedLogin(permissions = {"User/S"})
    @ApiOperation(value="分页获取所有用户信息", notes="")
    @GetMapping("page/")
    public ResponseData<IPage<UserInfo>> listPage(@Valid UserFilterForm userFilterForm, @Valid PageForm pageForm){

        ResponseData result = result(userService.list(userFilterForm));
        return result;
    }

    @NeedLogin(permissions = {"User/D"})
    @ApiOperation(value="删除用户", notes="")
    @DeleteMapping("{id}")
    public Response delete(@PathVariable("id") String id){

        return result();
    }

}
