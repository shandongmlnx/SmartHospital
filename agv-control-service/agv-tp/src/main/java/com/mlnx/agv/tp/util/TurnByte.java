package com.mlnx.agv.tp.util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 16进制与BYTE转换
 *
 * @author zzb
 * @create 2019/3/14 8:57
 */
public class TurnByte {

    /**
     * 字节转十六进制
     * @param b 需要进行转换的byte字节
     * @return  转换后的Hex字符串
     */
    public static String byteToHex(byte b){
        String hex = Integer.toHexString(b & 0xFF);
        if(hex.length() < 2){
            hex = "0" + hex;
        }
        return hex;
    }


    /**
     * 字节数组转16进制
     * @param bytes 需要转换的byte数组
     * @return  转换后的Hex字符串
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if(hex.length() < 2){
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }


    /**
     * Hex字符串转byte
     * @param inHex 待转换的Hex字符串
     * @return  转换后的byte
     */
    public static byte hexToByte(String inHex){
        return (byte)Integer.parseInt(inHex,16);
    }

    public static byte[] hexToByteArray(String inHex){
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1){
            //奇数
            hexlen++;
            result = new byte[(hexlen/2)];
            inHex="0"+inHex;
        }else {
            //偶数
            result = new byte[(hexlen/2)];
        }
        int j=0;
        for (int i = 0; i < hexlen; i+=2){
            result[j]=hexToByte(inHex.substring(i,i+2));
            j++;
        }
        return result;

    }
    public static byte[] addBytes(byte[] arg1,byte[] arg2){

        byte[] bt3 = new byte[arg1.length+arg2.length];
        System.arraycopy(arg1, 0, bt3, 0, arg1.length);
        System.arraycopy(arg2, 0, bt3, arg1.length, arg2.length);
        return bt3;

    }
    public static void main(String[] args) throws Exception{
//        ByteBuffer buf= ByteBuffer.allocate(102400);
//        List<Integer> positionList=new ArrayList<>();
//        positionList.add(1);
//        positionList.add(2);
//        positionList.add(3);
//        if(2==2){
//            for (Integer postion:positionList
//                    ) {
//
//                byte[] bp={(byte)0xfa,(byte) postion.intValue()};
//                buf.put(bp);
//            }
//            buf.flip();
//
//            System.out.println(buf.remaining());
//
//            byte[] bt=new byte[buf.remaining()];
//            buf.get(bt);
//
//            System.out.println(TurnByte.bytesToHex(bt));
//        }

//        AtomicInteger atomicInteger = new AtomicInteger();
//        int a=0;
//        for (int i=0;i<100;i++){
//             a=atomicInteger.getAndIncrement();
//          atomicInteger.compareAndSet(50,0);
//
//
//            System.out.println(a);
//        }
//        int year1=11;
//        int month1=2;
//        int day1=3;
//        String a=String.valueOf(year1)+month1+day1;
//        System.out.println(a);
      int i=20;

//        0001fbdc
       int a=  (byte)0x00 & 0x000000ff;
       int b=(byte)0x01 & 0x000000ff;
        int r=  (byte)0xfb & 0x000000ff;
        int d=  (byte)0xdc & 0x000000ff;
        int c=0<<24|1<<16|251<<8|220;
        System.out.println(c);
    }
}
