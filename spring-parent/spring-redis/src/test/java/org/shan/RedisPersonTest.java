package org.shan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.shan.bean.Person;
import org.shan.spring.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by amanda.shan on 2018/11/28.
 */
@RunWith(SpringRunner.class)
public class RedisPersonTest {

    @Autowired
    RedisUtil<Person> redisUtil;

    @Test
    public void test() {

        redisUtil.set("name", new Person(1, "shan", 33));

        Person person = redisUtil.getObject(redisUtil.get("name"), Person.class);
        ;

        System.out.println(person);

    }
}
