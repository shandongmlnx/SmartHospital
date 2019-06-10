package com.mlnx.agv.tp.body;

import lombok.Data;



/**
 * @author zzb
 * @create 2019/5/28 10:59
 */
@Data
public class AgvState {
   private int electricQuantity;//电量
   private String agvNum;//小车标号
   private int currentPositon;//当前坐标
   private int nextPosition;//下一个目标位置
   private String state;//小车状态/空闲或者进行中
   private String errorNum;//错误代码


}
