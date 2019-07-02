package com.mlnx.smart.user.service.iml;

import com.mlnx.common.utils.MyLog;
import com.mlnx.smart.user.TestUserApplication;
import com.mlnx.smart.user.entity.UserInfo;
import com.mlnx.smart.user.service.UserService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by amanda.shan on 2019/3/26.
 */
public class UserServiceImlTest extends TestUserApplication {

    private MyLog log = MyLog.getLog(getClass());

    @Autowired
    private UserService userService;


    @Test
    public void register() {

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("zhang");
        userInfo.setName("张医生");
        userInfo.setMobile("15824503913");
        userInfo.setPassword("123456");
        userInfo.setCard("987654321");
        userInfo.setFinger1(new byte[1000]);
        userInfo.setFinger2(new byte[1000]);
        userInfo.setFinger3(new byte[1000]);
        userInfo.setFace(new byte[1000]);


        userService.register(userInfo);
    }

    @Test
    public void getUserInfoVo() {
        UserInfo userInfoVo = userService.getUserInfoByName("zhang");

        log.info(userInfoVo.toString());
    }
}