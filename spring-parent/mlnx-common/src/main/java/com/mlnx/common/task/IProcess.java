package com.mlnx.common.task;


import com.mlnx.common.entity.DelayMessage;

/**
 * Created by amanda.shan on 2018/5/11.
 */
public interface IProcess {

    void process(DelayMessage delayMessage);
}
