package com.mlnx.agv.tp.body;

import lombok.Data;

/**
 * Agv小车任务状态反馈
 *
 * @author zzb
 * @create 2019/6/3 14:45
 */
@Data
public class AgvReInfo {
    private String agvNum;//小车编号
    private int nextPosition;//下个目标位置
    private String onlyTaskId;//小车任务编号
}
