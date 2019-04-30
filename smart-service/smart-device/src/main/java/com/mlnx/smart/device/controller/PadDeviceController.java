package com.mlnx.smart.device.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mlnx.common.entity.ResponseData;
import com.mlnx.common.form.PageForm;
import com.mlnx.smart.device.pojo.vo.PadDeviceVo;
import com.mlnx.smart.device.service.IPadDeviceService;

import org.shan.spring.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by amanda.shan on 2019/3/26.
 */
@Api("平板设备接口")
@RestController
@RequestMapping("/device/pad")
public class PadDeviceController extends BaseController {

    @Autowired
    private IPadDeviceService padDeviceService;

    @ApiOperation(value="分页获取平板设备")
    @GetMapping
    public ResponseData<IPage<PadDeviceVo>> list(@Valid PageForm pageForm){
        return result(padDeviceService.list(pageForm));
    }


}
