package com.mlnx.smart.auth.service.iml;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mlnx.smart.auth.entity.UserInfo;
import com.mlnx.smart.auth.mapper.UserInfoMapper;
import com.mlnx.smart.auth.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by amanda.shan on 2019/6/20.
 */
@Service
public class UserServiceIml implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo findByUsername(String username) {
        return userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("username", username));
    }

    @Override
    public UserInfo findByPhone(String mobile) {
        return userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("mobile", mobile));
    }

    @Override
    public UserInfo findByCard(String card) {
        return userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("card", card));
    }

    @Override
    public UserInfo findById(String id) {
        return userInfoMapper.selectById(id);
    }
}
