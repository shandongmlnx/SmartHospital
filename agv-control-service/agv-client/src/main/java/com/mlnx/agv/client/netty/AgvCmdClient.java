package com.mlnx.agv.client.netty;

import com.mlnx.agv.client.protocol.AgvCmdResponse;
import com.mlnx.agv.client.utils.SyncFuture;
import com.mlnx.common.utils.MyLog;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by amanda.shan on 2019/1/3.
 */
public class AgvCmdClient {

    private MyLog log = MyLog.getLog(getClass());

    public ChannelFuture channelFuture;
    private Bootstrap bootstrap;

    private static Map<Integer, SyncFuture<AgvCmdResponse>> syncFutureMap = new
            ConcurrentHashMap<>();

    public static SyncFuture<AgvCmdResponse> getFuture(Integer messageId) {
        return syncFutureMap.get(messageId);
    }


    public AgvCmdClient(String hostname, int port) throws InterruptedException {

        EventLoopGroup workGroup = new NioEventLoopGroup();

        bootstrap = new Bootstrap();
        bootstrap.group(workGroup).channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(hostname, port)) // 绑定连接端口和host信息
                .handler(new AgvCmdChannelInitializer()).option(ChannelOption.SO_BACKLOG, 1024);
    }

    public AgvCmdResponse sendCmd() throws Exception {

        checkConnect();

        SyncFuture<AgvCmdResponse> syncFuture = new SyncFuture<>();

        AgvCmdResponse response = syncFuture.get(10, TimeUnit.SECONDS);

        return response;
    }

    public void close() {
        channelFuture.channel().close();
    }

    private void checkConnect() throws InterruptedException {
        if (channelFuture == null || !channelFuture.channel().isActive()) {
            log.info("重新连接服务器");
            channelFuture = bootstrap.connect().sync();
        }
    }

}
