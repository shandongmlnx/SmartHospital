package com.mlnx.smart.user.service.iml;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mlnx.common.form.PageForm;
import com.mlnx.smart.user.dao.UserInfoMapper;
import com.mlnx.smart.user.entity.UserInfo;
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
        if (!StringUtils.isEmpty(userFilterForm.getMobile())) {
            columnMap.put("mobile", userFilterForm.getMobile());
        }

        return userInfoMapper.selectList(queryWrapper(userFilterForm));
    }

    @Override
    public IPage<UserInfo> listPage(UserFilterForm userFilterForm, PageForm pageForm) {

        IPage<UserInfo> userInfoIPage = userInfoMapper.selectPage(new Page<UserInfo>(pageForm.getCurrent(),
                pageForm.getSize()), queryWrapper(userFilterForm));
        return userInfoIPage;
    }

    private QueryWrapper queryWrapper(UserFilterForm userFilterForm){
        QueryWrapper wrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(userFilterForm.getMobile())) {
            wrapper.eq("mobile", userFilterForm.getMobile());
        }

        return wrapper;
    }
}
