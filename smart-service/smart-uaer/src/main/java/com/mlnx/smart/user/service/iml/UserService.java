package com.mlnx.smart.user.service.iml;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mlnx.common.form.PageForm;
import com.mlnx.smart.user.entity.UserInfo;
import com.mlnx.smart.user.mapper.UserInfoMapper;
import com.mlnx.smart.user.pojo.form.UserRegisterForm;
import com.mlnx.smart.user.pojo.vo.UserInfoVo;
import com.mlnx.smart.user.service.IUserService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

/**
 * Created by amanda.shan on 2019/3/26.
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public void register(UserRegisterForm userRegisterForm) {

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userRegisterForm, userInfo);

        userInfoMapper.insert(userInfo);
    }

    @Override
    public IPage<UserInfoVo> listUserInfo(@Valid PageForm pageForm) {
        return userInfoMapper.selectUserInfoVoPage(new Page<>(pageForm.getCurrent(), pageForm.getSize()));
    }
}
