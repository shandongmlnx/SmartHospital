package com.mlnx.agv.tp.body;


import com.mlnx.agv.tp.util.DecodeCRC16;
import com.mlnx.agv.tp.util.ReadUtils;
import com.mlnx.agv.tp.util.TurnByte;
import com.mlnx.agv.tp.util.TurnTask;
import com.mlnx.common.utils.MyLog;
import lombok.Data;

import java.nio.ByteBuffer;
import java.text.ParseException;
import java.util.List;


/**
 * @author zzb
 * @create 2019/5/27 15:56
 */
@Data
public class Body {
    MyLog logger = MyLog.getLog(Body.class);
    private static final ByteBuffer buffer = ByteBuffer.allocate(5000);
    private Command command;
    private ErrorType errorType;
    private AgvState agvState;
    private List<Integer> list;//坐标集合
    private String taskId;//小车任务号
    private AgvReInfo agvReInfo;//任务状态反馈


    public void decode(ByteBuffer buf) throws ParseException {
        logger.debug("进入DECODE");
        command = Command.decode(ReadUtils.readDataCmd(buf));
        switch (command) {
            case CALL_AGV:
                byte[] bt1 = new byte[12];
                buf.get(bt1);
                int year1 = bt1[3] & 0x000000ff;
                int month1 = bt1[4] & 0x000000ff;
                int day1 = bt1[5] & 0x000000ff;
                int six = bt1[6] & 0x000000ff;
                int five = bt1[7] & 0x000000ff;
                int four = bt1[8] & 0x000000ff;
                int three = bt1[9] & 0x000000ff;
                int two = bt1[10] & 0x000000ff;
                int one = bt1[11] & 0x000000ff;
                taskId = String.valueOf(year1) + String.format("%02d", month1) + String.format("%02d", day1) + six + five
                        + four + three + two + one;

                logger.debug("taskId为:" + taskId);
                break;
            case SEND_SHOP:
                byte[] bt2 = new byte[12];
                buf.get(bt2);
                int year2 = bt2[3] & 0x000000ff;
                int month2 = bt2[4] & 0x000000ff;
                int day2 = bt2[5] & 0x000000ff;
                int six2 = bt2[6] & 0x000000ff;
                int five2 = bt2[7] & 0x000000ff;
                int four2 = bt2[8] & 0x000000ff;
                int three2 = bt2[9] & 0x000000ff;
                int two2 = bt2[10] & 0x000000ff;
                int one2 = bt2[11] & 0x000000ff;
                taskId = String.valueOf(year2) + String.format("%02d", month2) + String.format("%02d", day2) + six2 + five2
                        + four2 + three2 + two2 + one2;
                logger.debug("taskId为:" + taskId);
                break;
            case NEXT_WAY:
                byte[] bt3 = new byte[12];
                buf.get(bt3);
                int year3 = bt3[3] & 0x000000ff;
                int month3 = bt3[4] & 0x000000ff;
                int day3 = bt3[5] & 0x000000ff;
                int six3 = bt3[6] & 0x000000ff;
                int five3 = bt3[7] & 0x000000ff;
                int four3 = bt3[8] & 0x000000ff;
                int three3 = bt3[9] & 0x000000ff;
                int two3 = bt3[10] & 0x000000ff;
                int one3 = bt3[11] & 0x000000ff;
                taskId = String.valueOf(year3) + String.format("%02d", month3) + String.format("%02d", day3) + six3 + five3
                        + four3 + three3 + two3 + one3;
                logger.debug("taskId为:" + taskId);
                break;
            case AGV_STATE:
                byte[] bt4 = new byte[39];
                buf.get(bt4);

                int a =bt4[4]& 0x000000ff;
                int b =bt4[5]& 0x000000ff;
                int c =bt4[6]& 0x000000ff;
                int d=bt4[7]& 0x000000ff;
                agvState.setCurrentPositon(a<<24|b<<16|
                        c<<8|d);//当前二维码码值
                System.out.println(a+"=="+b+"=="+
                      c+"=="+d);
                agvState.setAgvNum(String.valueOf(bt4[18]& 0x000000ff<<8|bt4[19]& 0x000000ff));
                int e=bt4[21]& 0x000000ff;
                int f=bt4[22]& 0x000000ff;
                agvState.setNextPosition(e<<8|f);
                agvState.setElectricQuantity(bt4[29]& 0x000000ff<<8|bt4[30]& 0x000000ff);
                agvState.setErrorNum(ErrorType.decode(bt4[33]& 0x000000ff).getDescription());
                break;
            case RETURN_TASK_INFO:
                byte[] bt5 = new byte[16];
                buf.get(bt5);
                int year5 = bt5[3] & 0x000000ff;
                int month5 = bt5[4] & 0x000000ff;
                int day5 = bt5[5] & 0x000000ff;
                int six5 = bt5[6] & 0x000000ff;
                int five5 = bt5[7] & 0x000000ff;
                int four5 = bt5[8] & 0x000000ff;
                int three5 = bt5[9] & 0x000000ff;
                int two5 = bt5[10] & 0x000000ff;
                int one5 = bt5[11] & 0x000000ff;
                taskId = String.valueOf(year5) + String.format("%02d", month5) + String.format("%02d", day5) + six5 + five5
                        + four5 + three5 + two5 + one5;
                agvReInfo.setOnlyTaskId(taskId);
                agvReInfo.setAgvNum(String.valueOf(bt5[12]& 0x000000ff<<8|bt5[13]& 0x000000ff));
                agvReInfo.setNextPosition(bt5[14]& 0x000000ff<<8|bt5[15]& 0x000000ff);
                break;
        }
    }


    public byte[] encode(int type, String onlytTaskId, List<Integer> positionList) {
        logger.debug("进入encode");
        buffer.clear();
        int length = positionList.size() * 2 + 10 + 12;

        if (type == 1) {//呼叫小车
            byte[] taskBtyes = TurnTask.turnBytes(onlytTaskId);
            byte[] b1 = {(byte) 0xFc, (byte) length, 0x00, 0x00, 0x00, 0x00,
                    (byte) 0x01};
            byte[] b2 = TurnByte.addBytes(b1, taskBtyes);
            byte[] b3 = {0x00, (byte) positionList.get(0).intValue()};
            byte[] b4 = TurnByte.addBytes(b2, b3);
            byte[] bt = DecodeCRC16.Crc16cal(b4);
            buffer.put(bt);

        } else if (type == 2) {//发货
            byte[] taskBtyes = TurnTask.turnBytes(onlytTaskId);
            byte[] b1 = {(byte) 0xFc, (byte) length, 0x00, 0x00, 0x00, 0x00,
                    (byte) 0x02};
            byte[] b2 = TurnByte.addBytes(b1, taskBtyes);

            ByteBuffer buf = ByteBuffer.allocate(5000);
            for (Integer postion : positionList
                    ) {//读取list中的位置数据
                byte[] bp = {0x00, (byte) postion.intValue()};
                buf.put(bp);
            }
            buf.flip();
            byte[] b3 = new byte[buf.remaining()];
            buf.get(b3);//BG为位置BYTE数据集合
            byte[] b4 = TurnByte.addBytes(b2, b3);
            byte[] bt = DecodeCRC16.Crc16cal(b4);
            buffer.put(bt);
//
        } else if (type == 3) {//下一步 去往下个一个坐标
            byte[] taskBtyes = TurnTask.turnBytes(onlytTaskId);
            byte[] b1 = {(byte) 0xFc, (byte) length, 0x00, 0x00, 0x00, 0x00,
                    (byte) 0x03};
            byte[] b2 = TurnByte.addBytes(b1, taskBtyes);
            byte[] b3 = {0x00, (byte) positionList.get(0).intValue()};
            byte[] b4 = TurnByte.addBytes(b2, b3);
            byte[] bt = DecodeCRC16.Crc16cal(b4);
            buffer.put(bt);
        }
        buffer.flip();
        byte[] bs = new byte[buffer.remaining()];
        buffer.get(bs);
        logger.debug("数据为:", TurnByte.bytesToHex(bs));
        return bs;
    }


    public void init() {
        agvState=new AgvState();
        agvReInfo=new AgvReInfo();
    }
}
