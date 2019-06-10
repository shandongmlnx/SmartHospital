package com.mlnx.agv.tp.util;


import com.mlnx.agv.tp.exception.InvalidPacketException;

import java.nio.ByteBuffer;

/**
 * 读取数据
 *
 * @author zzb
 * @create 2019/3/27 10:13
 */
public class ReadUtils {
    /**
     * 读取数据
     *
     * @param frame
     * @return
     * @throws InvalidPacketException
     */
    public static int readData(ByteBuffer frame)
            throws InvalidPacketException {
        byte[] bs = new byte[1];
        frame.get(bs);
        int data = 0;
        data = bs[0] & 0x000000ff;
        return data;
    }
    /**
     * 读取数据
     *
     * @param frame
     * @return
     * @throws InvalidPacketException
     */
    public static int readDataCmd(ByteBuffer frame)
            throws InvalidPacketException {
        byte[] bs = new byte[5];
        frame.get(bs);
        int data = 0;
        data = bs[4] & 0x000000ff;
        return data;
    }

    public static String readString(ByteBuffer frame)
            throws InvalidPacketException {
        byte[] bs = new byte[12];
        frame.get(bs);
        return new String(bs);
    }




//    public static BpBody readBpData(ByteBuffer frame)
//            throws InvalidPacketException {
//        byte[] bs = new byte[15];
//        frame.get(bs);
//        int sbp=bs[2] & 0x000000ff;
//        int dbp=bs[4] & 0x000000ff;
//        int hr=bs[6] & 0x000000ff;
//        int year = bs[7] & 0x000000ff;
//        int mouth = bs[8] & 0x000000ff;
//        int day = bs[9] & 0x000000ff;
//        int hour = bs[10] & 0x000000ff;
//        int min = bs[11] & 0x000000ff;
//        return data;
//    }
}




