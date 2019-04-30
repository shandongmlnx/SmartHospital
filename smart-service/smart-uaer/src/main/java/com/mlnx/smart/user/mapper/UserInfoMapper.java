package com.mlnx.smart.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mlnx.smart.user.entity.UserInfo;
import com.mlnx.smart.user.pojo.vo.UserInfoVo;

/**
 * Created by amanda.shan on 2019/3/26.
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    IPage<UserInfoVo> selectUserInfoVoPage(IPage<UserInfoVo> page);
}
