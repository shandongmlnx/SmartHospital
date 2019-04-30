package com.mlnx.smart.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mlnx.common.form.PageForm;
import com.mlnx.smart.user.pojo.form.UserRegisterForm;
import com.mlnx.smart.user.pojo.vo.UserInfoVo;

/**
 * Created by amanda.shan on 2019/3/26.
 */
public interface IUserService {

    void register(UserRegisterForm userRegisterForm);

    IPage<UserInfoVo> listUserInfo(PageForm pageForm);
}
