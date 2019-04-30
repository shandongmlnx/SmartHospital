package org.shan.spring.redis;

import com.mlnx.common.utils.MyLog;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;

import java.util.List;
import java.util.UUID;

/**
 * Created by amanda.shan on 2018/11/30.
 * 分布式锁实现
 */
public class RedisLock {

    private RedisUtil<String> redisUtil;

    private MyLog log = MyLog.getLog(RedisLock.class);

    /**
     * 重试时间
     */
    private static final int DEFAULT_ACQUIRY_RETRY_MILLIS = 10;
    /**
     * 锁的后缀
     */
    private static final String LOCK_SUFFIX = "_redis_lock";
    /**
     * 锁的key
     */
    private String lockKey;
    /**
     * 锁超时时间，防止线程在入锁以后，防止阻塞后面的线程无法获取锁
     */
    private int expireSecs = 20;
    /**
     * 线程获取锁的等待时间
     */
    private int timeoutMsecs = 10 * 1000;
    /**
     * 是否锁定标志
     */
    private volatile boolean locked = false;

    /**
     * 构造器
     *
     * @param redisUtil
     * @param lockKey   锁的key
     */
    public RedisLock(RedisUtil<String> redisUtil, String lockKey) {
        this.redisUtil = redisUtil;
        this.lockKey = lockKey + LOCK_SUFFIX;
    }

    /**
     * 构造器
     *
     * @param redisUtil
     * @param lockKey      锁的key
     * @param timeoutMsecs 获取锁的超时时间
     */
    public RedisLock(RedisUtil<String> redisUtil, String lockKey, int timeoutMsecs) {
        this(redisUtil, lockKey);
        this.timeoutMsecs = timeoutMsecs;
    }

    /**
     * 构造器
     *
     * @param redisUtil
     * @param lockKey      锁的key
     * @param timeoutMsecs 获取锁的超时时间
     * @param expireSecs  锁的有效期
     */
    public RedisLock(RedisUtil<String> redisUtil, String lockKey, int timeoutMsecs, int expireSecs) {
        this(redisUtil, lockKey, timeoutMsecs);
        this.expireSecs = expireSecs;
    }

    public String getLockKey() {
        return lockKey;
    }


    /**
     * 获取锁
     *
     * @return 获取锁成功返回ture，超时返回false
     * @throws InterruptedException
     */
    public synchronized String lock() throws InterruptedException {

        int timeout = timeoutMsecs;
        String value = UUID.randomUUID().toString();

        while (timeout >= 0) {

            if (redisUtil.setNX(lockKey, value)) {
                redisUtil.expire(lockKey, expireSecs);
                return value;
            }else if (redisUtil.getExpire(lockKey) == -1){
                redisUtil.expire(lockKey, expireSecs);
            }
            timeout -= DEFAULT_ACQUIRY_RETRY_MILLIS;
            //延时
            Thread.sleep(DEFAULT_ACQUIRY_RETRY_MILLIS);
        }
        return null;
    }

    /**
     * 释放获取到的锁
     */
    public synchronized boolean unlock(String value) {

        // 判断是否是自己持有的那个锁
        String v = redisUtil.get(lockKey);
        if (value.equals(v)) {
            List<Object> results =  redisUtil.execute(new SessionCallback<List<Object>>() {
                @Override
                public List<Object> execute(RedisOperations operations) throws DataAccessException {
                    operations.watch(lockKey);
                    operations.multi();
                    operations.delete(lockKey);
                    return operations.exec();
                }
            });

            if (results.size() > 0 && "1".equals(results.get(0).toString())){
                return true;
            }else{
                log.error("解锁失败:"+results);
                return false;
            }
        }else{
            log.error(String.format("当前锁已经被别的用户获取,本线程value：%s, 真实value：%s", value, v));
        }

        return false;
    }

}
