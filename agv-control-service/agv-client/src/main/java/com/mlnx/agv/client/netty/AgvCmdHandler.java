package com.mlnx.agv.client.netty;

import com.mlnx.agv.client.protocol.AgvCmdResponse;
import com.mlnx.agv.client.utils.SyncFuture;
import com.mlnx.agv.tp.AgvPacket;
import com.mlnx.common.utils.MyLog;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by amanda.shan on 2019/1/3.
 */
public class AgvCmdHandler extends SimpleChannelInboundHandler<AgvPacket> {

    MyLog log = MyLog.getLog(getClass());



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AgvPacket agvPacket) {

        AgvCmdResponse response = new AgvCmdResponse();
        if(agvPacket.getBody().getTaskId()!=null){
            response.setOnlyTaskId(agvPacket.getBody().getTaskId());
            response.setSucess(true);
            SyncFuture<AgvCmdResponse> future = AgvCmdClient.getFuture(response.getOnlyTaskId());
            future.setResponse(response);
        }

    }
}
