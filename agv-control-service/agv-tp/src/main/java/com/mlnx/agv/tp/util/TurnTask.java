package com.mlnx.agv.tp.util;

import jdk.internal.dynalink.beans.StaticClass;

/**
 * @author zzb
 * @create 2019/5/29 14:28
 */
public class TurnTask {
    public static String turn(int taskId) {
        String str=null;
        if(taskId<10){
            str="00000"+taskId;
        }else if(taskId>=10&&taskId<100){
            str="0000"+taskId;
        }else if(taskId>=100&&taskId<1000){
            str="000"+taskId;
        }else if(taskId>=1000&&taskId<10000){
            str="00"+taskId;
        }else if(taskId>=10000&&taskId<100000){
            str="0"+taskId;
        }else if(taskId>=100000&&taskId<1000000){
            str=String.valueOf(taskId);
        }
        return str;
    }
    public static byte[] turnBytes(String taskId) {
        int year =Integer.valueOf(taskId.substring(0,2));
        int month =Integer.valueOf(taskId.substring(2,4));
        int day =Integer.valueOf(taskId.substring(4,6));
        int six=Integer.valueOf(taskId.substring(6,7));
        int five=Integer.valueOf(taskId.substring(7,8));
        int four=Integer.valueOf(taskId.substring(8,9));
        int three=Integer.valueOf(taskId.substring(9,10));
        int two=Integer.valueOf(taskId.substring(10,11));
        int one=Integer.valueOf(taskId.substring(11,12));
        byte[] b={0x00,0x00,0x00,(byte) year,(byte) month,
                (byte) day,(byte) six,(byte) five,
                (byte) four,(byte) three,(byte) two,(byte)one};
        return b;
    }

    public static void main(String[] args) {
//        System.out.println(TurnTask.turn(123456));
//        System.out.println(String.format("%02d", 11));
//        String str ="112233";
//        String str1=str.substring(2,4);
//        System.out.println(str1);
        byte[] b={(byte)0xfa,(byte)0x31};
        System.out.println(TurnByte.bytesToHex(DecodeCRC16.Crc16cal1(b)));
        String str="fc310000000006000000000001fbdc00000000000000000000000100ffff00000000000000350000000000000000";
        byte[] c=TurnByte.hexToByteArray(str);
        System.out.println(TurnByte.bytesToHex(DecodeCRC16.Crc16cal1(c)));
    }
}
