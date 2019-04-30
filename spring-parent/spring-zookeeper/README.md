
## 配置

配置ZK地址
```
// 单个
mlnx.zookeeper.connectionInfo=192.168.1.176:2181

// 集群
mlnx.zookeeper.connectionInfo=192.168.1.170:2181,192.168.1.174:2181,192.168.1.188:2181
```

## 分布式锁

分布式条件下，多个进程同时抢占一个资源

1）、zk路径，同个进程只要配置同一个地址，就是对同一个资源加锁
2）、运行方法体


```
 lock.lockRuning("/shan/zk", new Runnable() {
    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName()+"开始运行");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+"结束运行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
});
```

## leader选举

### 使用场景

1、定时任务：在分布式条件下，有一个任务被分布在不同的服务器中，但是只能一次有一个运行；

### 示例

```
// spring启动的时候把他启动起来
latch.start("/shan/leader", null);

// 任务执行的时候，下面代码返回boolean，来判断当前是否可以执行
latch.isLeader("/shan/leader")；

```


## 多个进程等待一个执行才执行

```
// 某个进程发送放行指令
 zkBarrier.setBarrier("/shan/barrier");
Thread.sleep(5000);
System.out.println("放行");
zkBarrier.go("/shan/barrier");

// 别的进程等待上面那个进程运行go方法
zkBarrier.wait("/shan/barrier");

```