package com.mlnx.smart.device.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mlnx.common.form.PageForm;
import com.mlnx.smart.device.entity.PadDevice;
import com.mlnx.smart.device.pojo.vo.PadDeviceVo;

/**
 * Created by amanda.shan on 2019/3/26.
 */
public interface IPadDeviceService {

    void add(PadDevice padDevice);

    IPage<PadDeviceVo> list(PageForm pageForm);

    PadDevice modify(PadDevice padDevice);

    PadDevice findById(Integer id);

    void remove(Integer id);
}
