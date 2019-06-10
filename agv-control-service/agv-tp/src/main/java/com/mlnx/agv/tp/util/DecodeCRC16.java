package com.mlnx.agv.tp.util;

/**
 * 校验方法
 *
 * @author zzb
 * @create 2019/3/20 14:06
 */
public class DecodeCRC16 {

    /**
     * 计算CRC16校验  对外的接口
     *
     * @param b 需要计算的数组
     * @return 原数据+CRC16校验值
     */
    public static byte[] Crc16cal1(byte b[])
    {
        int p[]=new int[b.length];
        for(int i=0;i<b.length;i++){
            p[i]=0x000000ff &b[i];
        }
        int j =p.length;
        int CL = 1;
        int CH = 0xA0;
        int SaveHi;
        int SaveLo;
        int  i;
        int Flag;
        int CRC16Lo = 0xff;
        int CRC16Hi = 0xff;

        for(i = 0; i < j; i++)
        {
            CRC16Lo = (CRC16Lo ^ p[i]);
            for(Flag = 0; Flag < 8; Flag++)
            {
                SaveHi = CRC16Hi;
                SaveLo = CRC16Lo;
                CRC16Hi = (CRC16Hi / 2);
                CRC16Lo = (CRC16Lo / 2);
                if((SaveHi & 0x1) == 1)
                {
                    CRC16Lo = (CRC16Lo | 0x80);
                }
                if((SaveLo & 0x1) == 1)
                {
                    CRC16Hi = (CRC16Hi ^ CH);
                    CRC16Lo = (CRC16Lo ^ CL);
                }
            }
        }
        int[] result=new int[]{CRC16Hi,CRC16Lo};
        byte[] b2={(byte)result[0],(byte)result[1]};
        byte[] bt3 = new byte[b.length+b2.length];
        System.arraycopy(b, 0, bt3, 0, b.length);
        System.arraycopy(b2, 0, bt3, b.length, b2.length);
//        System.out.println("CRC16Hi = " + CRC16Hi + "CRC16Lo = " + CRC16Lo);
//        return (int) (CRC16Hi << 8 | CRC16Lo);
        return bt3;
    }
    /**
     * 计算CRC16校验  对外的接口
     *
     * @param b 需要计算的数组
     * @return 原数据+CRC16校验值+结束码
     */
    public static byte[] Crc16cal(byte b[])
    {
        int p[]=new int[b.length];
        for(int i=0;i<b.length;i++){
            p[i]=0x000000ff &b[i];
        }
        int j =p.length;
        int CL = 1;
        int CH = 0xA0;
        int SaveHi;
        int SaveLo;
        int  i;
        int Flag;
        int CRC16Lo = 0xff;
        int CRC16Hi = 0xff;

        for(i = 0; i < j; i++)
        {
            CRC16Lo = (CRC16Lo ^ p[i]);
            for(Flag = 0; Flag < 8; Flag++)
            {
                SaveHi = CRC16Hi;
                SaveLo = CRC16Lo;
                CRC16Hi = (CRC16Hi / 2);
                CRC16Lo = (CRC16Lo / 2);
                if((SaveHi & 0x1) == 1)
                {
                    CRC16Lo = (CRC16Lo | 0x80);
                }
                if((SaveLo & 0x1) == 1)
                {
                    CRC16Hi = (CRC16Hi ^ CH);
                    CRC16Lo = (CRC16Lo ^ CL);
                }
            }
        }
        int[] result=new int[]{CRC16Hi,CRC16Lo};
        byte[] b2={(byte)result[0],(byte)result[1],(byte)0x5a};
        byte[] bt3 = new byte[b.length+b2.length];
        System.arraycopy(b, 0, bt3, 0, b.length);
        System.arraycopy(b2, 0, bt3, b.length, b2.length);
//        System.out.println("CRC16Hi = " + CRC16Hi + "CRC16Lo = " + CRC16Lo);
//        return (int) (CRC16Hi << 8 | CRC16Lo);
        return bt3;
    }

}

