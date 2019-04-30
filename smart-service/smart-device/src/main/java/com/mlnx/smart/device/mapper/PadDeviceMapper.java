package com.mlnx.smart.device.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mlnx.smart.device.entity.PadDevice;
import com.mlnx.smart.device.pojo.vo.PadDeviceVo;

/**
 * Created by amanda.shan on 2019/3/26.
 */
public interface PadDeviceMapper extends BaseMapper<PadDevice> {

    IPage<PadDeviceVo> selectPadDeviceVoPage(IPage iPage);
}
