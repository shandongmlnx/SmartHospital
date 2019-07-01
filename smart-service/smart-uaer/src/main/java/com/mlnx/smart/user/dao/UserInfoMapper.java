package com.mlnx.smart.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mlnx.smart.user.entity.UserInfo;
import com.mlnx.smart.user.pojo.vo.UserInfoVo;

import java.util.List;

/**
 * Created by amanda.shan on 2019/3/26.
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    UserInfoVo selectUserInfoVoByName(String username);

    List<UserInfoVo> selectUserInfoVoByMobile(String mobile);

}
