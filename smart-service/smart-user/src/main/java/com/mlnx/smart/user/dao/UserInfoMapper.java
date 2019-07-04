package com.mlnx.smart.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mlnx.smart.user.entity.UserInfo;
import com.mlnx.smart.user.pojo.vo.UserInfoVo;

/**
 * Created by amanda.shan on 2019/3/26.
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    UserInfoVo selectUserInfoVoByName(String username);

    /**
     * 列出 UserInfoVo 这种方式列出的用户都是角色为 normal的角色
     * @param page
     * @return
     */
    Page<UserInfoVo> listUserInfoVo(Page<UserInfoVo> page);

}
