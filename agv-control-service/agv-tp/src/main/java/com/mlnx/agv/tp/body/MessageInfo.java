package com.mlnx.agv.tp.body;

/**
 * @author zzb
 * @create 2019/3/25 13:56
 */
public enum MessageInfo {
    FAIL_1(1, "数据库操作失败"), FAIL_2(2, "调出区域使用出错，无调出区的配置信息")
    , FAIL_3(3, "调入区域使用出错，无调入区的配置信息"), FAIL_4(4,"调出区AGV数量不足")
    , FAIL_5(5, "调入区待机位置不足");

    private int code;
    private String description;

    private MessageInfo(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static MessageInfo decode(int code) {
        MessageInfo[] commands = MessageInfo.values();
        for (int i = 0; i < commands.length; i++) {

            if (code == commands[i].code)
                return commands[i];
        }
        return null;
    }

}
