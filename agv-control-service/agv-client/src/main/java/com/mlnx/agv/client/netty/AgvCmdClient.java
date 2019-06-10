package com.mlnx.agv.client.netty;

import com.mlnx.agv.client.protocol.AgvCmdResponse;
import com.mlnx.agv.client.utils.SyncFuture;
import com.mlnx.agv.tp.AgvPacket;
import com.mlnx.agv.tp.body.Body;
import com.mlnx.agv.tp.util.TurnTask;
import com.mlnx.common.utils.MyLog;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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

    private static Map<String, SyncFuture<AgvCmdResponse>> syncFutureMap = new
            ConcurrentHashMap<>();

    public static SyncFuture<AgvCmdResponse> getFuture(String taskId) {

        return syncFutureMap.get(taskId);
    }


    public AgvCmdClient(String hostname, int port) throws InterruptedException {

        EventLoopGroup workGroup = new NioEventLoopGroup();

        bootstrap = new Bootstrap();
        bootstrap.group(workGroup).channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(hostname, port)) // 绑定连接端口和host信息
                .handler(new AgvCmdChannelInitializer()).option(ChannelOption.SO_BACKLOG, 1024);
    }

    public AgvCmdResponse sendCmd(int type,List<Integer> list,int taskId) throws Exception {

        checkConnect();

        SyncFuture<AgvCmdResponse> syncFuture = new SyncFuture<>();
        Date date1=new Date();
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        c.setTime(date1);
        int year = c.get(Calendar.YEAR)-2000;
        int month = c.get(Calendar.MONTH)+1;
        int date = c.get(Calendar.DATE);
        String onlyTask=String.valueOf(year)+String.format("%02d",month)+String.format("%02d",date)+TurnTask.turn(taskId);//形成唯一任务号
        AgvPacket agvPacket=new AgvPacket();
        byte[] bt = agvPacket.encodeType(type,onlyTask,list);
        syncFutureMap.put(onlyTask, syncFuture);
        channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer(bt));
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

    public static void main(String[] args) throws Exception  {
        AgvCmdClient client = new AgvCmdClient("localhost",9099);
        List<Integer>  list =new ArrayList<>();
        int taskId=1;
        list.add(2);
//        list.add(3);
        AgvCmdResponse response=client.sendCmd(3,list,taskId);
        System.out.println(response.getOnlyTaskId()+"=="+response.isSucess());

    }

}
