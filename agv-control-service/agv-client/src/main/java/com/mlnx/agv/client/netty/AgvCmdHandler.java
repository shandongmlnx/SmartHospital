package com.mlnx.agv.client.netty;

import com.mlnx.agv.client.protocol.AgvCmdResponse;
import com.mlnx.agv.client.utils.SyncFuture;
import com.mlnx.common.utils.MyLog;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by amanda.shan on 2019/1/3.
 */
public class AgvCmdHandler extends SimpleChannelInboundHandler<ByteBuf> {

    MyLog log = MyLog.getLog(getClass());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {

        AgvCmdResponse response = null;
        SyncFuture<AgvCmdResponse> future = AgvCmdClient.getFuture(response.getMessageId());
        future.setResponse(response);

    }
}
