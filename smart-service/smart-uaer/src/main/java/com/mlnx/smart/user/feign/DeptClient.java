package com.mlnx.smart.user.feign;

import com.mlnx.common.entity.ResponseData;
import com.mlnx.smart.annotation.MlnxFeignServer;
import com.mlnx.smart.user.entity.DeptInfo;
import com.mlnx.smart.user.service.IDeptService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import io.swagger.annotations.ApiOperation;

/**
 * Created by amanda.shan on 2019/3/28.
 */
@ApiOperation("部门client接口")
@MlnxFeignServer
@RequestMapping("/dept")
public class DeptClient implements IDeptClient {

    @Autowired
    private IDeptService deptService;

    @ApiOperation(value="根据ID获取部门列表")
    @PostMapping("/findByIds")
    @Override
    public ResponseData<List<DeptInfo>> findByIds(@RequestBody List<Integer> deptIds) {
        return new ResponseData<List<DeptInfo>>(deptService.findByIds(deptIds));
    }
}
