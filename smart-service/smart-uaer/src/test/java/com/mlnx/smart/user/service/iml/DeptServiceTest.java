package com.mlnx.smart.user.service.iml;

import com.mlnx.smart.user.TestUserApplication;
import com.mlnx.smart.user.entity.DeptInfo;
import com.mlnx.smart.user.service.IDeptService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by amanda.shan on 2019/3/26.
 */
public class DeptServiceTest extends TestUserApplication {

    @Autowired
    private IDeptService deptService;

    @Test
    public void add() throws Exception {

        deptService.add(new DeptInfo("外科","2楼"));
        deptService.add(new DeptInfo("呼吸科","3楼"));
        deptService.add(new DeptInfo("内科","4楼"));
    }

    @Test
    public void list() throws Exception {

    }

    @Test
    public void modify() throws Exception {

    }

    @Test
    public void remove() throws Exception {

    }

}