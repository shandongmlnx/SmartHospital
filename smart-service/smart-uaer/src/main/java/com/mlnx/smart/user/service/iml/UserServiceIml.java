package com.mlnx.smart.user.service.iml;

import com.mlnx.smart.user.entity.UserInfo;
import com.mlnx.smart.user.dao.UserInfoMapper;
import com.mlnx.smart.user.pojo.form.UserFilterForm;
import com.mlnx.smart.user.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by amanda.shan on 2019/3/26.
 */
@Service
public class UserServiceIml implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void register(UserInfo userInfo) {

        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));

        userInfoMapper.insert(userInfo);
    }

    @Override
    public void modify(UserInfo userInfo) {

    }

    @Override
    public List<UserInfo> list(UserFilterForm userFilterForm) {

        Map<String, Object> columnMap = new HashMap<>();
        if (!StringUtils.isEmpty(userFilterForm.getMobile())){
            columnMap.put("mobile", userFilterForm.getMobile());
        }

        return userInfoMapper.selectByMap(columnMap);
    }
}
