package org.shan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.shan.spring.redis.RedisLock;
import org.shan.spring.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by amanda.shan on 2018/11/28.
 */
@RunWith(SpringRunner.class)
public class LockTest {

    @Autowired
    RedisUtil<String> redisUtil;

    @Test
    public void test() throws InterruptedException {

        RedisLock redisLock = new RedisLock(redisUtil, "test");
        String value = redisLock.lock();
        System.out.println(redisLock.unlock(value));
    }

    @Test
    public void testMutil() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    while (true){

                        try {
                            RedisLock redisLock = new RedisLock(redisUtil, "test");
                            String value = null;
                            value = redisLock.lock();
                            if (value != null){
                                System.out.println(Thread.currentThread()+" 加锁成功");
                                doWorlk();
                                if (redisLock.unlock(value)){
                                    System.out.println(Thread.currentThread()+" 解锁成功\n");
                                }else{
                                    System.out.println(Thread.currentThread()+" 解锁失败\n");
                                }

                                Thread.sleep(5000);
                            }else{
                                System.out.println(Thread.currentThread()+" 加锁失败\n");
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        Thread.sleep(100000000);
    }


    private int share = 0;
    private void doWorlk(){
        System.out.println(Thread.currentThread()+"执行任务........");
        share++;

        if (share > 2){
            throw new RuntimeException(Thread.currentThread()+" error");
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        share = 0;
    }

}
