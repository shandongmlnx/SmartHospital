package org.shan.spring.zookeeper.leader;

import com.mlnx.common.utils.MyLog;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.shan.spring.zookeeper.lock.ZookeeperLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by amanda.shan on 2018/12/10.
 */
@Component
public class ZKLeaderLatch {

    MyLog log = MyLog.getLog(ZookeeperLock.class);

    @Autowired
    private CuratorFramework client;

    private ConcurrentHashMap<String, LeaderLatch> leaderLatchMap = new ConcurrentHashMap<>();

    public void start(String path, String id) throws Exception {
        if (leaderLatchMap.get(path) != null){
            log.warn(String.format("%s leader 已经启动成功", path));
            return;
        }
        LeaderLatch latch = null;
        if (id!= null) {
            latch = new LeaderLatch(client, path, id);
        }else{
            latch = new LeaderLatch(client, path);
        }
        latch.start();
        leaderLatchMap.put("path", latch);
    }

    public boolean isLeader(String path){
        if (leaderLatchMap.get(path)  == null){
            return false;
        }
        return leaderLatchMap.get(path).hasLeadership();
    }


}
