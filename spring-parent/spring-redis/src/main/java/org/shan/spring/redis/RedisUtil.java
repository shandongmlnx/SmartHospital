package org.shan.spring.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

/**
 * Created by fzh on 2018/2/26.
 */
public class RedisUtil<T> {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    private RedisConnection redisConnection;

    private RedisSerializer stringSerializer = new StringRedisSerializer();
    private FastJsonRedisSerializer objectFastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);

    @PostConstruct
    public void init() {
        redisConnection = redisConnectionFactory.getConnection();

        RedisZSetCommands commands = redisConnection.zSetCommands();
        System.out.println();
    }

    private RedisTemplate<String, T> redisTemplate;

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<String, T> getRedisTemplate() {
        return redisTemplate;
    }

    //=============================common============================


    /**
     * key的string序列化
     *
     * @param key
     * @return
     */
    public byte[] keyStrSerializer(Object key) {
        return stringSerializer.serialize(key);
    }

    /**
     * value的json序列化
     *
     * @param value
     * @return
     */
    public byte[] valueJsonSerializer(Object value) {
        return objectFastJsonRedisSerializer.serialize(value);
    }


    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        if (time > 0) {
            return redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
        return false;
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回-1代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public Object del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                return redisTemplate.delete(key[0]);
            } else {
                return redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }

        return false;
    }

    //============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public T get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
        return true;
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, T value, long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            return set(key, value);
        }
        return true;
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    public Boolean setNX(String key, T value){
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    //================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
        return true;
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, T> map, long time) {
        redisTemplate.opsForHash().putAll(key, map);
        if (time > 0) {
            expire(key, time);
        }
        return true;
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, T value) {
        redisTemplate.opsForHash().put(key, item, value);
        return true;
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, T value, long time) {
        redisTemplate.opsForHash().put(key, item, value);
        if (time > 0) {
            expire(key, time);
        }
        return true;
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, T... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    //============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<T> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, T value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, T... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, T... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long sRemove(String key, T... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    public List<T> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public T lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, T value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, T value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, List<T> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, List<T> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, T value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, T value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * key失效时间
     *
     * @param key
     * @return
     */
    public long ttl(String key) {
        try {
            Long ttl = redisTemplate.getExpire(key);
            return ttl;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    /****************************有序列表********************/

    /**
     * 向zset有序列表中添加
     *
     * @param key
     * @param value
     * @param score
     * @return
     */
    public boolean zadd(String key, T value, double score) {
        try {
            boolean zadd = redisTemplate.opsForZSet().add(key, value, score);
            return zadd;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取该key的元素数量
     *
     * @param key
     * @return
     */
    public long zCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 从zset中取出指定区间的元素(根据下标查询)元素从小到大
     *
     * @param key
     * @param start
     * @param stop
     * @return
     */
    public Set<T> zrange(String key, long start, long stop) {
        Set<T> zset = new HashSet<>();
        try {
            zset = redisTemplate.opsForZSet().range(key, start, stop);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zset;
    }

    /**
     * 从zset中取出指定区间的元素(根据下标查询)元素从大到小
     *
     * @param key
     * @param start
     * @param stop
     * @return
     */
    public Set<T> zreverseRange(String key, long start, long stop) {
        Set<T> zset = new HashSet<>();
        try {
            zset = redisTemplate.opsForZSet().reverseRange(key, start, stop);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zset;
    }

    /**
     * 两个有序集合取交集，分数默认是相加
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public Set<T> intersectAndStore(String key, String otherKey, String destKey) {
        // 返回交集的值的数量
        long s = redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);

        return zrange(destKey, 0, -1);

    }

    /**
     * 从zset中取出指定分数范围的元素
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<T> zrangeByScore(String key, double min, double max, long offset, long count) {
        Set<T> zset = new HashSet<>();
        try {
            zset = redisTemplate.opsForZSet().rangeByScore(key, min, max, offset, count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zset;
    }

    public Set<T> zrangeByScore(String key, double min, double max) {
        Set<T> zset = new HashSet<>();
        try {
            zset = redisTemplate.opsForZSet().rangeByScore(key, min, max);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zset;
    }

    /**
     * 从zset中删除指定元素
     *
     * @param key
     * @param value
     * @return
     */
    public Long remove(String key, T value) {
        try {
            Long remove = redisTemplate.opsForZSet().remove(key, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    /**
     * 从zset中删除指定分数范围的元素
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long removeByScore(String key, double min, double max) {
        Long remove = -1L;
        try {
            remove = redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return remove;
    }

    /**
     * 从zset中取出指倒数几个的元素
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<T> reverseRange(String key, long start, long end) {
        Set<T> zset = new HashSet<>();
        try {
            zset = redisTemplate.opsForZSet().reverseRange(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return zset;
    }


    /**
     * 事务执行
     *
     * @param session
     * @param <T>
     * @return
     */
    public <T> T execute(SessionCallback<T> session) {
        return redisTemplate.execute(session);
    }

    /**
     * 批量执行命令
     *
     * @param action
     * @return
     */
    public List<Object> executePipelined(RedisCallback<?> action) {
        return redisTemplate.executePipelined(action);
    }

    public List<Object> executePipelined(SessionCallback<?> session) {
        return redisTemplate.executePipelined(session);
    }


    public <T> T getObject(Object json, Class<T> clazz) {
        if (json == null) {
            return null;
        }
        return JSON.parseObject(json.toString(), clazz);
    }

    public <T> List<T> getObjectList(Object json, Class<T> clazz) {
        if (json == null) {
            return null;
        }
        return JSON.parseArray(json.toString(), clazz);
    }


    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());

        /**
         *  9223372036854775807
         9223372036854776

         1543367720828
         1543367720828E12
         */

        double v = System.currentTimeMillis();
        System.out.println(v);
        System.out.println(Double.MAX_VALUE);
    }
}
