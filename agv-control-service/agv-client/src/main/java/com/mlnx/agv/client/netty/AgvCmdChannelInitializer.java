package com.mlnx.agv.client.netty;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Created by amanda.shan on 2019/1/3.
 */
public class AgvCmdChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline p = ch.pipeline();

        p.addLast(new LengthFieldBasedFrameDecoder(
                1024, 2, 2));
        p.addLast(new AgvCmdHandler());

    }
}
