package com.mlnx.agv.server.netty;


import com.mlnx.agv.client.netty.AgvServerChannelInitializer;
import com.mlnx.common.utils.MyLog;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author zzb
 * @create 2019/6/3 15:52
 */
public class NettyConfig {
    MyLog logger = MyLog.getLog(NettyConfig.class);
    private EventLoopGroup bossGroup ;
    private EventLoopGroup workGroup;

    private ChannelFuture channelFuture;

    public NettyConfig(EventLoopGroup bossGroup, EventLoopGroup workGroup) {
        super();
        this.bossGroup = bossGroup;
        this.workGroup = workGroup;
    }


//    public void start() throws Exception {
//
//        start(new AgvCmdChannelInitializer());
//    }


    public void start() throws Exception {

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
//                     .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new AgvServerChannelInitializer())
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            channelFuture = b.bind(9099).sync(); // (7)

//            channelFuture.channel().closeFuture().sync();
            System.out.println(9099);
            logger.info("agv  utils start ok port:" + 9099);
        } finally {
            // 在jvm关闭的时候执行
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    shutdown();
                }
            });
        }
    }

    public void shutdown() {

        if (channelFuture != null) {
            channelFuture.channel().close().syncUninterruptibly();
            channelFuture = null;
        }
    }

    public void restart() throws Exception {
        shutdown();
        start();
    }


    public static void main(String[] args)throws Exception{

//        EventLoopGroup pGroup = new NioEventLoopGroup();
//        EventLoopGroup WGroup = new NioEventLoopGroup();
//
//        new AgvCmdServer(pGroup,WGroup).start();


    }

}
