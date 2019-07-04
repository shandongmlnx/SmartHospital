package com.mlnx.smart.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mlnx.common.annotations.NeedLogin;
import com.mlnx.common.entity.Response;
import com.mlnx.common.entity.ResponseData;
import com.mlnx.common.form.PageForm;
import com.mlnx.smart.user.entity.UserInfo;
import com.mlnx.smart.user.pojo.form.UserFilterForm;
import com.mlnx.smart.user.pojo.vo.UserInfoVo;
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
    @ApiOperation(value="分页获取所有用户详细信息", notes="")
    @GetMapping("/details")
    public ResponseData<IPage<UserInfo>> listDetailPage(@Valid UserFilterForm userFilterForm, @Valid PageForm pageForm){

        ResponseData result = result(userService.listPage(userFilterForm, pageForm));
        return result;
    }

    @NeedLogin(permissions = {"User/S"})
    @ApiOperation(value="分页获取所有用户精简信息", notes="")
    @GetMapping("/simples")
    public ResponseData<IPage<UserInfoVo>> listSimplePage(@Valid UserFilterForm userFilterForm, @Valid PageForm pageForm){

        ResponseData result = result(userService.listUserInfoVoPage(userFilterForm, pageForm));
        return result;
    }

    @NeedLogin(permissions = {"User/D"})
    @ApiOperation(value="删除用户", notes="")
    @DeleteMapping("{id}")
    public Response delete(@PathVariable("id") String id){

        return result();
    }

    @NeedLogin
    @ApiOperation(value="根据用户名获取用户", notes="")
    @GetMapping("username/{username}/")
    public Response findByName(@PathVariable("username") String username){

        return result(userService.getUserInfoByName(username));
    }

    @NeedLogin
    @ApiOperation(value="获取自身信息", notes="")
    @GetMapping("self/")
    public Response self(){

        UserInfo userInfo = getAttribute("user");
        return result(userInfo);
    }


}
