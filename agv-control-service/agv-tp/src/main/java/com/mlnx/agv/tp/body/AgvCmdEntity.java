package com.mlnx.agv.tp.body;

import lombok.Data;

import java.util.List;

/**
 * @author zzb
 * @create 2019/6/3 11:12
 */
@Data
public class AgvCmdEntity {
    private  int type;
    private List<Integer> list;
}
