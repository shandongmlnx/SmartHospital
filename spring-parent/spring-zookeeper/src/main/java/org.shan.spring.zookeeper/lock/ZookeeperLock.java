package org.shan.spring.zookeeper.lock;

import com.mlnx.common.utils.MyLog;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by amanda.shan on 2018/12/10.
 */
@Component
public class ZookeeperLock {

    MyLog log = MyLog.getLog(ZookeeperLock.class);

    @Autowired
    private CuratorFramework client;

    private ConcurrentHashMap<String, InterProcessMutex> mutexConcurrentHashMap = new ConcurrentHashMap<>();

    /**
     * 加锁运行
     * @param path      zookeeper路径
     * @param runnable  运行对象
     */
    public void lockRuning(String path, Runnable runnable){

        InterProcessMutex lock = mutexConcurrentHashMap.get(path);
        if (mutexConcurrentHashMap.get(path) == null){
            lock = new InterProcessMutex(client, path);
            mutexConcurrentHashMap.put(path, lock);
        }
        try {
            //加锁
            lock.acquire();
            runnable.run();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            try {
                //释放
                lock.release();
            } catch (Exception e) {
                log.error(e.getMessage(),e);
            }
        }
    }
}
