package com.mlnx.agv.client.netty;



import com.mlnx.agv.tp.AgvPacket;
import com.mlnx.agv.tp.util.TurnByte;
import com.mlnx.common.utils.MyLog;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ReferenceCountUtil;

import java.util.List;


public class AgvDecode extends ByteToMessageDecoder {
    private MyLog logger=MyLog.getLog(AgvDecode.class);

    enum State {
        HEAD, LEN, CONTANT
    }

    private State state = State.HEAD;

    //    private int matchHeadIndex;
    private int length;

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
            throws Exception {
        while (true) {
            switch (state) {
                case HEAD:
                    if (matchHead(in)) {
//                        System.out.println("匹配到头文件");
                        state = State.LEN;

                    } else {
                        return;
                    }
                case LEN:
                    if (in.isReadable()) {

//                        length=in.readableBytes();
                        length = 0x000000ff & in.readByte();
                        state = State.CONTANT;
                        logger.info("获得长度"+length);
//                        byte[] b=new byte[in.readableBytes()];
//                        in.readBytes(b);
//                        logger.info("获得长度"+TurnByte.bytesToHex(b));
                    } else {
                        return;
                    }
                case CONTANT:
                    if (in.isReadable(length - 2)) {

//                        if(in.getByte(length-3)!=(byte) 0x5A){
//                            logger.info("bbbbbbbbb");
//                            return;
//                        }
                        ByteBuf frame=null;
                        try {
                             frame = in.readBytes(length - 2);
                            AgvPacket agvPacket = new AgvPacket();
                            agvPacket.decode(frame.nioBuffer());
                            out.add(agvPacket);
                        } catch (Exception e) {
//                        LogUtils.e("recive  contant:" + ByteBufUtil.hexDump(frame));
                            logger.info("receive Exception:",e);
                        } finally {
                            ReferenceCountUtil.release(frame);
                            state = State.HEAD;
                        }
                    } else {
                        return;
                    }
            }
        }

    }

    private boolean matchHead(ByteBuf buf) {

        while (buf.isReadable()) {
            byte b = buf.readByte();
//           int i=0x000000ff & b;
//            System.out.println(i);
            if (b == (byte) 0xFC) {
//                System.out.println("连接到设备");
                return true;
            }
//            if (b == head.Heads[matchHeadIndex]) {
//                matchHeadIndex++;
//            } else if (b == head.Heads[0]) {
//                matchHeadIndex = 1;
//            } else {
//                matchHeadIndex = 0;
//            }
        }
        return false;
    }
//    private int getPacketLength(ByteBuf buf) {
//        if (buf.isReadable(4)) {
//            byte[] dst = new byte[4];
//            buf.readBytes(dst);
//            int length = ByteUtils.bytesToInt(dst, 4);
//            System.out.println("ddddddddddddddddddd");
//            return length;
//        } else {
//            System.out.println("进入到-1");
//            return -1;
//        }
//    }

}
