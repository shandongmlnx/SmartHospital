package com.mlnx.smart.auth.service;

import com.mlnx.smart.auth.entity.UserInfo;

/**
 * Created by amanda.shan on 2019/6/20.
 */
public interface UserService {

    UserInfo findByUsername(String username);

    UserInfo findByPhone(String phone);

    UserInfo findByCard(String card);

    UserInfo findById(String id);
}
