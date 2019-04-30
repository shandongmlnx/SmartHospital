package com.mlnx.common;


import com.mlnx.common.entity.DelayMessage;
import com.mlnx.common.task.DelayedTask;
import com.mlnx.common.task.DelayedTaskConsumer;
import com.mlnx.common.task.IProcess;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by amanda.shan on 2018/5/11.
 */
public class TestDelayTask {

    public static void main(String[] args) {
        final DelayedTask delayedTask = new DelayedTask();

//        for (int i = 0; i < 10; i++) {
//            DelayMessage delayMessage = new DelayMessage(2 + "", "", i * 1000);
//            delayedTask.add(delayMessage);
//        }

        ExecutorService exec = Executors.newCachedThreadPool();
        DelayedTaskConsumer consumer = new DelayedTaskConsumer(delayedTask.getQueue(), new IProcess() {
            @Override
            public void process(DelayMessage delayMessage) {
                System.out.println(delayMessage.toString());
            }
        });
        exec.execute(consumer);

        addTask(exec, delayedTask);
//        addTask(exec, delayedTask);
//        addTask(exec, delayedTask);
//        addTask(exec, delayedTask);
//        addTask(exec, delayedTask);
    }

    private static void addTask(ExecutorService exec, final DelayedTask delayedTask) {
        exec.execute(new Runnable() {
            @Override
            public void run() {

                Random random = new Random();
                while (true) {

                    DelayMessage delayMessage = new DelayMessage(random.nextInt(10) + "", "", random.nextInt(2) *
                            1000+System.currentTimeMillis());
                    delayedTask.add(delayMessage);

                    System.out.println("加入DelayMessage:"+delayMessage.getId()+"----"+delayMessage.getDelay(TimeUnit.MILLISECONDS));

                    try {
                        Thread.sleep(new Random().nextInt(4)*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}
