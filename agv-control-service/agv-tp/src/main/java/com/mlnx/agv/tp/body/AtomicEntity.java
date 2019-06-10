package com.mlnx.agv.tp.body;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zzb
 * @create 2019/6/3 10:59
 */
public class AtomicEntity {
    private static AtomicInteger atomicInteger = new AtomicInteger();
    public static int getTaskId() {
        atomicInteger.compareAndSet(999999,0);
        return atomicInteger.incrementAndGet();
    }
}
