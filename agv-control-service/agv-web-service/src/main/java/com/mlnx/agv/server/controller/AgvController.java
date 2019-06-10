package com.mlnx.agv.server.controller;

import com.mlnx.agv.client.netty.AgvCmdClient;
import com.mlnx.agv.client.protocol.AgvCmdResponse;
import com.mlnx.agv.tp.body.AgvCmdEntity;
import com.mlnx.agv.tp.body.AtomicEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by amanda.shan on 2019/5/27.
 * Agv指令 app 接口
 */
@RestController
@RequestMapping(value = "/data")
public class AgvController {
    ////    @Autowired
//    private AgvCmdClient agvCmdClient;
    @PostMapping(value = "agv/cmd")
    public Map<String ,Object> setAgvCmd(@RequestBody AgvCmdEntity agvCmdEntity){
        Map<String ,Object> map=new HashMap<>();
        int taskId=AtomicEntity.getTaskId();
        try {
//            agvCmdClient=new AgvCmdClient("localhost",9099);
            AgvCmdClient client = new AgvCmdClient("192.168.1.158",9100);
//            List<Integer>  list =new ArrayList<>();
//            list.add(2);
//        list.add(3);
            AgvCmdResponse response=client.sendCmd(agvCmdEntity.getType(),agvCmdEntity.getList(),taskId);

            System.out.println(response.getOnlyTaskId()+"=="+response.isSucess());
            map.put("发送成功",response.getOnlyTaskId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    @PostMapping(value = "agv/test")
    public Map<String ,Object> setTest(@RequestBody AgvCmdEntity agvCmdEntity){
        Map<String ,Object> map=new HashMap<>();
        int taskId=AtomicEntity.getTaskId();
//        try {
////            agvCmdClient=new AgvCmdClient("localhost",9099);
//            AgvCmdClient client = new AgvCmdClient("192.168.1.158",9100);
////            List<Integer>  list =new ArrayList<>();
////            list.add(2);
////        list.add(3);
//            AgvCmdResponse response=client.sendCmd(agvCmdEntity.getType(),agvCmdEntity.getList(),taskId);
//
//            System.out.println(response.getOnlyTaskId()+"=="+response.isSucess());
//            map.put("发送成功",response.getOnlyTaskId());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        map.put("回复",agvCmdEntity.getType());
        return map;
    }
}
