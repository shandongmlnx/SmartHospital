package org.shan.zookeeper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.shan.spring.zookeeper.SpringZookeeperApplication;
import org.shan.spring.zookeeper.barrier.ZkBarrier;
import org.shan.spring.zookeeper.leader.ZKLeaderLatch;
import org.shan.spring.zookeeper.lock.ZookeeperLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/**
 * Created by amanda.shan on 2018/12/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringZookeeperApplication.class)
public class ZkTest {

    @Autowired
    ZookeeperLock lock;

    @Autowired
    ZKLeaderLatch latch;

    @Autowired
    ZkBarrier zkBarrier;

    @Test
    public void testLock(){

        CountDownLatch latch = new CountDownLatch(2);

        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    lock.lockRuning("/shan/zk", new Runnable() {
                        @Override
                        public void run() {
                            try {
                                System.out.println(Thread.currentThread().getName()+"开始运行");
                                Thread.sleep(2000);
                                System.out.println(Thread.currentThread().getName()+"结束运行");
                                latch.countDown();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testZKLeaderLatch(){
        try {
            latch.start("/shan/leader", null);
            while (latch.isLeader("/shan/leader")){
                Thread.sleep(100);
            }
            System.out.println("获取到leader");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBarrier() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    zkBarrier.setBarrier("/shan/barrier");
                    Thread.sleep(5000);
                    System.out.println("放行");
                    zkBarrier.go("/shan/barrier");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(1000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    zkBarrier.wait("/shan/barrier");
                    System.out.println("等待线程运行");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(60000);
    }
}
