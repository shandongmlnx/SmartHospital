package com.mlnx.smart.device.service.iml;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mlnx.common.entity.ResponseData;
import com.mlnx.common.form.PageForm;
import com.mlnx.smart.common.exception.RemoteCallException;
import com.mlnx.smart.device.entity.PadDevice;
import com.mlnx.smart.device.mapper.PadDeviceMapper;
import com.mlnx.smart.device.pojo.vo.PadDeviceVo;
import com.mlnx.smart.device.service.IPadDeviceService;
import com.mlnx.smart.user.entity.DeptInfo;
import com.mlnx.smart.user.feign.IDeptClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amanda.shan on 2019/3/26.
 */
@Service
public class PadDeviceService implements IPadDeviceService {

    @Autowired
    private PadDeviceMapper padDeviceMapper;

    @Autowired
    private IDeptClient deptClient;

    @Override
    public void add(PadDevice padDevice) {
        padDeviceMapper.insert(padDevice);
    }

    @Override
    public IPage<PadDeviceVo> list(PageForm pageForm) {

        // 查询设备信息
        IPage<PadDeviceVo> padDeviceIPage = padDeviceMapper.selectPadDeviceVoPage(new Page<>(pageForm.getCurrent(), pageForm
                .getSize()));

        // 查询设备所属部门信息
        List<Integer> deptIds = new ArrayList<>();
        for (PadDevice padDevice: padDeviceIPage.getRecords()) {
            deptIds.add(padDevice.getDeptId());
        }
        ResponseData<List<DeptInfo>> responseData = deptClient.findByIds(deptIds);
        if (!responseData.isSucess()){
            throw new RemoteCallException("获取部门信息失败："+responseData.toString());
        }

        // 聚合数据
        List<DeptInfo> deptInfos = (List<DeptInfo>) responseData.getObj();

        for (PadDeviceVo padDeviceVo: padDeviceIPage.getRecords()) {
            for (DeptInfo deptInfo: deptInfos) {
                if (deptInfo.getId().equals(padDeviceVo.getDeptId())){
                    padDeviceVo.setDeptName(deptInfo.getDeptName());
                    padDeviceVo.setPostion(deptInfo.getPostion());
                    break;
                }
            }
        }


        return padDeviceIPage;
    }

    @Override
    public PadDevice modify(PadDevice padDevice) {
        padDeviceMapper.updateById(padDevice);
        return padDevice;
    }

    @Override
    public PadDevice findById(Integer id) {
        return padDeviceMapper.selectById(id);
    }

    @Override
    public void remove(Integer id) {
        padDeviceMapper.deleteById(id);
    }
}
