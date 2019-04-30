package com.mlnx.common.task;


import com.mlnx.common.entity.DelayMessage;

import java.util.concurrent.DelayQueue;

/**
 * Created by amanda.shan on 2018/5/11.
 */
public class DelayedTaskConsumer implements Runnable{

    private DelayQueue<DelayMessage> queue;
    private boolean isRun;
    private IProcess iProcess;

    public DelayedTaskConsumer(DelayQueue<DelayMessage> queue, IProcess iProcess) {
        this.queue = queue;
        this.iProcess = iProcess;
    }

    @Override
    public void run() {
        isRun = true;
        while (isRun){
            try {
                DelayMessage delayMessage = queue.take();//此处会阻塞
                iProcess.process(delayMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cancell(){
        isRun = false;
    }
}
