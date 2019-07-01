package com.mlnx.smart.auth.service.iml;

import com.mlnx.smart.auth.TestAuthApplication;
import com.mlnx.smart.auth.service.UserService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by amanda.shan on 2019/6/20.
 */
public class UserServiceImlTest extends TestAuthApplication {

    @Autowired
    private UserService userService;

    @Test
    public void findByPhone() {

        System.out.println(userService.findByPhone("15824503913"));
    }

    @Test
    public void findByCard() {

        System.out.println(userService.findByCard("888888"));
    }

    @Test
    public void findById() {
        System.out.println(userService.findByCard("888888"));
    }
}