package com.mlnx.smart.device.service.iml;

import com.alibaba.fastjson.JSON;
import com.mlnx.smart.device.TestDeviceApplication;
import com.mlnx.smart.device.entity.PadDevice;
import com.mlnx.smart.device.enums.DeviceTypeEnum;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by amanda.shan on 2019/3/29.
 */
public class PadDeviceServiceTest extends TestDeviceApplication{

    @Autowired
    private PadDeviceService padDeviceService;

    @Test
    public void add() throws Exception {

        PadDevice padDevice = new PadDevice();
        padDevice.setDeviceType(DeviceTypeEnum.PAD.getValue());
        padDevice.setDeptId(1);
        padDeviceService.add(padDevice);

        padDevice.setDeptId(2);
        padDeviceService.add(padDevice);

        padDevice.setDeptId(3);
        padDeviceService.add(padDevice);
    }

    @Test
    public void list() throws Exception {

        System.out.println(JSON.toJSON(null));
    }

    @Test
    public void modify() throws Exception {

    }

    @Test
    public void findById() throws Exception {

    }

    @Test
    public void remove() throws Exception {

    }

}