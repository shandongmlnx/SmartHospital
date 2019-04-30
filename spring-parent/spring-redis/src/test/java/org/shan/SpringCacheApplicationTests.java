package org.shan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.shan.spring.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
public class SpringCacheApplicationTests {

    @Autowired
    RedisUtil<String> redisUtil;

    @Test
    public void testZSet() {
        for (int i = 0; i < 10; i++) {
            redisUtil.zadd("zset1", "shan" + i, i);
        }

        Set<String> strings = redisUtil.zreverseRange("zset", 0, 1);
        System.out.println(strings);

    }

    @Test
    public void testInsertStore() {

        RedisTemplate template = redisUtil.getRedisTemplate();
        long s = template.opsForZSet().intersectAndStore("zset", "zset1", "result");
        System.out.println(s);
        Set<ZSetOperations.TypedTuple<String>> strings = template.opsForZSet().rangeWithScores("result", 0, -1);

        for (ZSetOperations.TypedTuple<String> tuple : strings) {
            System.out.println(tuple.getValue() + " " + tuple.getScore());
        }
    }


    @Test
    public void testTransactions() {

        List<Object> txResults = redisUtil.execute(new SessionCallback<List<Object>>() {
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                operations.watch("age");
                operations.multi();
                operations.opsForValue().set("age", "11");
                operations.opsForValue().get("name");

                return operations.exec();
            }
        });
        System.out.println(txResults);

    }

    @Test
    public void testPipelined() {

//        List<Object> results = redisUtil.executePipelined(
//                new RedisCallback<Object>() {
//                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
//
//                        connection.multi();
//                        for (int i = 0; i < 10; i++) {
//                            connection.rPush(redisUtil.keyStrSerializer("myqueue"),
//                                    redisUtil.valueJsonSerializer(i + ""));
//                        }
//                        connection.exec();
//                        return null;
//                    }
//                });

        List<Object> results = redisUtil.getRedisTemplate().executePipelined(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {

                operations.watch("age");
                operations.multi();
                operations.opsForValue().set("age", "11");
                operations.opsForValue().get("name");
                List<Object> list =  operations.exec();
                System.out.println(list);

                return null;
            }
        });

        System.out.println(results);
    }


}
