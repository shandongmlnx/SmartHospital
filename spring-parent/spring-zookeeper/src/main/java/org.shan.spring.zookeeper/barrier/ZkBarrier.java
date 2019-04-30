package org.shan.spring.zookeeper.barrier;

import com.mlnx.common.utils.MyLog;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.shan.spring.zookeeper.lock.ZookeeperLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by amanda.shan on 2018/12/10.
 */
@Component
public class ZkBarrier {

    MyLog log = MyLog.getLog(ZookeeperLock.class);

    @Autowired
    private CuratorFramework client;

    private ConcurrentHashMap<String, DistributedBarrier> leaderLatchMap = new ConcurrentHashMap<>();

    public void setBarrier(String path) throws Exception {
        if (leaderLatchMap.get(path) == null) {
            DistributedBarrier controlBarrier = new DistributedBarrier(client, path);
            controlBarrier.setBarrier();
            leaderLatchMap.put(path, controlBarrier);
        }else{
            log.warn(String.format("%s Barrier 已经存在", path));
        }
    }

    public void wait(String path) throws Exception {

        DistributedBarrier controlBarrier = leaderLatchMap.get(path);
        if (leaderLatchMap.get(path) == null) {
            controlBarrier = new DistributedBarrier(client, path);
            leaderLatchMap.put(path, controlBarrier);
        }

        leaderLatchMap.get(path).waitOnBarrier();
    }

    public void go(String path) throws Exception {
        if (leaderLatchMap.get(path) == null){
            return;
        }
        leaderLatchMap.get(path).removeBarrier();
    }
}
