package com.mlnx.agv.server.netty;

import com.mlnx.agv.client.netty.AgvCmdChannelInitializer;
import com.mlnx.common.utils.MyLog;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by amanda.shan on 2019/5/27.
 * <p>
 * AgV 系统指令服务
 */
@Component
public class AgvCmdServer {
    MyLog logger = MyLog.getLog(AgvCmdServer.class);
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workGroup = new NioEventLoopGroup();
    private NettyConfig nettyConfig;



    @PostConstruct
    public void start() throws Exception {

        try {


            nettyConfig = new NettyConfig(bossGroup, workGroup);
            nettyConfig.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restart() throws Exception {
        nettyConfig.restart();

    }

    public void shutdown() {

        nettyConfig.shutdown();

    }

    public static void main(String[] args) throws Exception {

//        EventLoopGroup pGroup = new NioEventLoopGroup();
//        EventLoopGroup WGroup = new NioEventLoopGroup();
//
//        new AgvCmdServer(pGroup,WGroup).start();


    }

}
