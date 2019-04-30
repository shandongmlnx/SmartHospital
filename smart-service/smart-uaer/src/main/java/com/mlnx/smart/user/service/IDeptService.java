package com.mlnx.smart.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mlnx.common.form.PageForm;
import com.mlnx.smart.user.entity.DeptInfo;

import java.util.List;

/**
 * Created by amanda.shan on 2019/3/26.
 */
public interface IDeptService {

    void add(DeptInfo deptInfo);

    IPage<DeptInfo> list(PageForm pageForm);

    DeptInfo modify(DeptInfo deptInfo);

    DeptInfo findById(Integer id);

    List<DeptInfo> findByIds(List<Integer> deptIds);

    void remove(Integer id);
}
