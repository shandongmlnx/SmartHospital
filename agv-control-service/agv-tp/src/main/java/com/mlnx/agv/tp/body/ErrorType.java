package com.mlnx.agv.tp.body;

/**
 * @author zzb
 * @create 2019/3/26 16:40
 */
public enum ErrorType {
    ERROR_0(0, "无错误"), ERROR_1(1, "陀螺仪通信超时"),
    ERROR_2(2, "陀螺仪初始化故障"), ERROR_3(3, "马达1通信超时"), ERROR_4(4, "马达2通信超时"), ERROR_5(5, "马达3通信超时"), ERROR_6(6, "马达4通信超时")
    , ERROR_7(7, "马达5通信超时"), ERROR_8(8, "马达1故障报警"), ERROR_9(9, "马达2故障报警"), ERROR_10(10, "马达3故障报警")
    , ERROR_11(11, "马达4故障报警"), ERROR_12(12, "马达5故障报警"), ERROR_13(13, "马达1控制故障"), ERROR_14(14, "马达2控制故障")
    , ERROR_15(15, "马达3控制故障"), ERROR_16(16, "马达4控制故障"), ERROR_17(17, "马达5控制故障"), ERROR_18(18, "前转角度控制故障"),  ERROR_19(19, "后转角度控制故障")
    , ERROR_20(20, "旋转马达位置初始化"), ERROR_21(21, "驱动升降故障")
    , ERROR_22(22, "急停按下报警"), ERROR_23(23, "路径偏移过大")
    , ERROR_24(24, "丢失目标站点故障，丢失二维码故障"), ERROR_25(25, "原地旋转偏差二维码过大")
    , ERROR_26(26, "頂盘参数在行程允许范围外"), ERROR_27(27, "頂盘未原点初始化")
    , ERROR_28(28, "无磁条信号"), ERROR_29(29, "码带导航命令出错"), ERROR_30(30, "导航模式站点不支持");

    private int code;
    private String description;

    private ErrorType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static ErrorType decode(int code) {
        ErrorType[] commands = ErrorType.values();
        for (int i = 0; i < commands.length; i++) {
            if (code == commands[i].code)
                return commands[i];
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




}
