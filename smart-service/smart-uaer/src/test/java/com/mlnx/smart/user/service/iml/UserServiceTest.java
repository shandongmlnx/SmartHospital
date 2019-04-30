package com.mlnx.smart.user.service.iml;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mlnx.common.form.PageForm;
import com.mlnx.smart.user.TestUserApplication;
import com.mlnx.smart.user.pojo.form.UserRegisterForm;
import com.mlnx.smart.user.pojo.vo.UserInfoVo;
import com.mlnx.smart.user.service.IUserService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by amanda.shan on 2019/3/26.
 */
public class UserServiceTest extends TestUserApplication {

    @Autowired
    IUserService userService;

    @Test
    public void register() throws Exception {

        UserRegisterForm userRegisterForm = new UserRegisterForm("yeye", "123456");

        userService.register(userRegisterForm);
    }

    @Test
    public void listUserInfo() throws Exception {

        IPage<UserInfoVo> infoVoIPage = userService.listUserInfo(new PageForm(1, 5));

        System.out.println(JSON.toJSON(infoVoIPage));
    }

}