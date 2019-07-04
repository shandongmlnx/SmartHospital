package com.mlnx.smart.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mlnx.common.form.PageForm;
import com.mlnx.smart.user.entity.UserInfo;
import com.mlnx.smart.user.pojo.form.UserFilterForm;
import com.mlnx.smart.user.pojo.vo.UserInfoVo;

/**
 * Created by amanda.shan on 2019/3/26.
 */
public interface UserService {

    void register(UserInfo userInfo);

    void modify(UserInfo userInfo);

    IPage<UserInfo> listPage(UserFilterForm userFilterForm, PageForm pageForm);

    IPage<UserInfoVo> listUserInfoVoPage(UserFilterForm userFilterForm, PageForm pageForm);

    UserInfo getUserInfoByName(String username);

}
