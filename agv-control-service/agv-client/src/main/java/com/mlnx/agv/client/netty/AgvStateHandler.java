package com.mlnx.agv.client.netty;

import com.mlnx.agv.tp.AgvPacket;
import com.mlnx.agv.tp.body.AgvReInfo;
import com.mlnx.agv.tp.body.AgvState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author zzb
 * @create 2019/6/5 14:56
 */
public class AgvStateHandler extends SimpleChannelInboundHandler<AgvPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AgvPacket agvPacket) throws Exception {
        if(agvPacket.getBody().getAgvState()!=null){
            AgvState agvState=agvPacket.getBody().getAgvState();
            System.out.println(agvState.toString());

        }else if(agvPacket.getBody().getAgvReInfo()!=null){
            AgvReInfo agvReInfo=agvPacket.getBody().getAgvReInfo();
            System.out.println(agvReInfo.toString());
        }
    }
}
