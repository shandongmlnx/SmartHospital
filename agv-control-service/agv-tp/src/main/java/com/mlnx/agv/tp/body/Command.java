package com.mlnx.agv.tp.body;

/**
 * @author zzb
 * @create 2019/3/25 13:56
 */
public enum Command {
    CALL_AGV(0x01, "呼叫小车"), SEND_SHOP(0x02, "发货"), NEXT_WAY(0x03, "下一步"),
    AGV_STATE(0X06,"AGV状态回复"), RETURN_TASK_INFO(0x04, "任务状态反馈");

    private int code;
    private String description;

    private Command(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Command decode(int code) {
        Command[] commands = Command.values();
        for (int i = 0; i < commands.length; i++) {

            if (code == commands[i].code)
                return commands[i];
        }
        return null;
    }

}
