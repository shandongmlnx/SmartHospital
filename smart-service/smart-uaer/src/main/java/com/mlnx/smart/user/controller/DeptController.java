package com.mlnx.smart.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mlnx.common.entity.Response;
import com.mlnx.common.entity.ResponseData;
import com.mlnx.common.form.PageForm;
import com.mlnx.smart.user.entity.DeptInfo;
import com.mlnx.smart.user.pojo.form.DeptInfoForm;
import com.mlnx.smart.user.service.IDeptService;

import org.shan.spring.base.BaseController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by amanda.shan on 2019/3/26.
 */
@Api("部门接口")
@RestController
@RequestMapping("/dept")
public class DeptController extends BaseController {

    @Autowired
    private IDeptService deptService;

    @ApiOperation(value="保存部门")
    @PostMapping()
    public Response add(@Valid DeptInfoForm deptInfoForm){

        DeptInfo deptInfo = new DeptInfo();
        BeanUtils.copyProperties(deptInfoForm, deptInfo);

        deptService.add(deptInfo);
        return result();
    }

    @ApiOperation(value="更新部门信息")
    @PutMapping("/{id}")
    public Response modify(@PathVariable Integer id, DeptInfoForm deptInfoForm){

        DeptInfo deptInfo = new DeptInfo();
        BeanUtils.copyProperties(deptInfoForm, deptInfo);
        deptInfo.setId(id);

        deptService.modify(deptInfo);
        return result();
    }

    @ApiOperation(value="删除部门信息")
    @DeleteMapping("/{id}")
    public Response remove(@PathVariable Integer id){

        deptService.remove(id);
        return result();
    }

    @ApiOperation(value="分页获取部门列表")
    @GetMapping
    public ResponseData<IPage<DeptInfo>> list(@Valid PageForm pageForm){
        return result(deptService.list(pageForm));
    }

    @ApiOperation(value="根据部门ID获取部门列表")
    @GetMapping("/{id}")
    public ResponseData<DeptInfo> findById(@PathVariable Integer id){
        return result(deptService.findById(id));
    }

}
