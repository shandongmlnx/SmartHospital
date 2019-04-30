package com.mlnx.smart.user.feign;

import com.mlnx.common.entity.ResponseData;
import com.mlnx.smart.annotation.MlnxFeignClient;
import com.mlnx.smart.user.entity.DeptInfo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by amanda.shan on 2019/3/28.
 */
@MlnxFeignClient(name = "DeptClient", url = "http://localhost:8081")
public interface IDeptClient {

    String API_PREFIX = "/dept/";

    @PostMapping(API_PREFIX+"findByIds")
    ResponseData<List<DeptInfo>> findByIds(@RequestBody List<Integer> deptIds);

}
