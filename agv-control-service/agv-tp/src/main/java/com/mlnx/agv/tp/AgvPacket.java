package com.mlnx.agv.tp;

import com.mlnx.agv.tp.body.Body;
import com.mlnx.agv.tp.exception.InvalidPacketException;
import com.mlnx.agv.tp.util.DecodeCRC16;
import com.mlnx.agv.tp.util.TurnByte;
import lombok.Data;

import java.nio.ByteBuffer;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zzb
 * @create 2019/3/25 12:26
 */
@Data
public class AgvPacket implements Codec {
    private static final ByteBuffer buffer = ByteBuffer
            .allocate(10000);

    public static final byte[] Heads = { (byte) 0xFC}; // 数据包包头字节
    public static boolean shouldCheckSum = true;
    private Body body;



    public AgvPacket() {
        super();
        init();
    }

    @Override
    public void init() {
        body = new Body();
        body.init();
    }

    @Override
    public void decode(ByteBuffer buf) throws ParseException {
        if(shouldCheckSum){
            checkSum(buf);
        }
        body.decode(buf);
    }

    @Override
    public byte[] encode() {
        synchronized (AgvPacket.class) {
//            buffer.clear();
//
//           byte[] bs = body.encode();
//            buffer.put(bs);
//            buffer.flip();
//            bs = new byte[buffer.remaining()];
//            buffer.get(bs);
            byte[] bs = {0x01};

            return bs;
        }
    }

    public byte[] encodeType( int type,String onlyTask, List<Integer> positionList) {
        synchronized (AgvPacket.class) {
            buffer.clear();

            byte[] bs = body.encode(type,onlyTask,positionList);
            buffer.put(bs);
            buffer.flip();
            bs = new byte[buffer.remaining()];
            buffer.get(bs);
            return bs;
        }
    }

//    public void decodeType(ByteBuffer buf) throws ParseException {
//        if(shouldCheckSum){
//            checkSum(buf);
//        }
//        body.decode(buf);
//    }


    public void checkSum(ByteBuffer buf) {
        buf.mark();
        int lengths=buf.remaining();
        byte[] bs = new byte[lengths];
        buf.get(bs);//除头和长度外的数据
        byte[] b1={(byte)0xFc,(byte)(lengths+2)};//添加完整数据头和长度
        byte[] b2 = new byte[lengths-3];//截取除了CRC16验证码和结束码的数据
//        LogUtils.e("除去FA和长度的数据："+ TurnByte.bytesToHex(bs));
//        System.out.println("除去FA和长度的数据："+TurnByte.bytesToHex(bs)+"数据长度："+bs.length);
        System.arraycopy(bs, 0, b2, 0, lengths-3);
        byte[] b3 = new byte[b1.length+b2.length];//将数据拼接完整

        System.arraycopy(b1, 0, b3, 0, b1.length);
        System.arraycopy(b2, 0, b3, b1.length, b2.length);
        byte[] b4=DecodeCRC16.Crc16cal(b3);//CRC16验证
        if(!(b4[lengths-1]==bs[lengths-3]&&b4[lengths]==bs[lengths-2])){
            throw new InvalidPacketException(String.format("checksum失败 收到CRC16:0x%x,0x%x  计算checksum:0x%x,0x%x",
                    bs[lengths-3], bs[lengths-2],b4[lengths-1], b4[lengths]));
        }
        buf.reset();
    }
    /**
     * pong 服务器回复设备
     */
    public static AgvPacket pong() {
        AgvPacket bpPacket=new AgvPacket();
//		LogUtils.i("pong");
        return bpPacket;
    }


}
