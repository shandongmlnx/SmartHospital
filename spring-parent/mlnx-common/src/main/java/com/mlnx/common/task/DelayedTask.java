package com.mlnx.common.task;


import com.mlnx.common.entity.DelayMessage;

import java.util.concurrent.DelayQueue;

/**
 * Created by amanda.shan on 2018/5/11.
 */
public class DelayedTask {

    private DelayQueue<DelayMessage> queue;

    public DelayedTask() {
        queue = new DelayQueue<DelayMessage>();
    }

    public DelayQueue<DelayMessage> getQueue() {
        return queue;
    }

    public void setQueue(DelayQueue<DelayMessage> queue) {
        this.queue = queue;
    }

    public void add(DelayMessage delayMessage){
        queue.add(delayMessage);
    }

    public boolean remove(DelayMessage delayMessage){
        return queue.remove(delayMessage);
    }

    public int size(){
        return queue.size();
    }
}
