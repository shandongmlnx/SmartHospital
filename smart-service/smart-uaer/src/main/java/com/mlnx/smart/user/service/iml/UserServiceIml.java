package com.mlnx.smart.user.service.iml;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mlnx.common.form.PageForm;
import com.mlnx.smart.user.dao.RoleMapper;
import com.mlnx.smart.user.dao.UserInfoMapper;
import com.mlnx.smart.user.dao.UserRoleMapper;
import com.mlnx.smart.user.entity.Role;
import com.mlnx.smart.user.entity.UserInfo;
import com.mlnx.smart.user.entity.UserRole;
import com.mlnx.smart.user.exception.UserException;
import com.mlnx.smart.user.pojo.form.UserFilterForm;
import com.mlnx.smart.user.pojo.vo.UserInfoVo;
import com.mlnx.smart.user.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by amanda.shan on 2019/3/26.
 */
@Service
public class UserServiceIml implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public void register(UserInfo userInfo) {

        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoMapper.insert(userInfo);

        if (userInfo.getId() == null){
            throw new UserException("用户表注册失败");
        }

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("role_key", "normal");
        Role role = roleMapper.selectOne(wrapper);

        if (role == null){
            throw new UserException("用户权限查询失败");
        }

        UserRole userRole = new UserRole(userInfo.getId(), role.getId());
        userRoleMapper.insert(userRole);
    }

    @Override
    public void modify(UserInfo userInfo) {

    }

    @Override
    public IPage<UserInfo> listPage(UserFilterForm userFilterForm, PageForm pageForm) {

        IPage<UserInfo> userInfoIPage = userInfoMapper.selectPage(new Page<UserInfo>(pageForm.getCurrent(),
                pageForm.getSize()), queryWrapper(userFilterForm));
        return userInfoIPage;
    }

    @Override
    public UserInfoVo getUserInfoVoByName(String username) {

        return userInfoMapper.selectUserInfoVoByName(username);
    }


    private QueryWrapper queryWrapper(UserFilterForm userFilterForm){
        QueryWrapper wrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(userFilterForm.getMobile())) {
            wrapper.eq("mobile", userFilterForm.getMobile());
        }

        return wrapper;
    }
}
