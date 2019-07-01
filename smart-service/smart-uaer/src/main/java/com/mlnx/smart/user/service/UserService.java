package com.mlnx.smart.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mlnx.common.form.PageForm;
import com.mlnx.smart.user.entity.UserInfo;
import com.mlnx.smart.user.pojo.form.UserFilterForm;

import java.util.List;

/**
 * Created by amanda.shan on 2019/3/26.
 */
public interface UserService {

    void register(UserInfo userInfo);

    void modify(UserInfo userInfo);

    List<UserInfo> list(UserFilterForm userFilterForm);


    IPage<UserInfo> listPage(UserFilterForm userFilterForm, PageForm pageForm);

}
