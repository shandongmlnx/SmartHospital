package org.shan.spring.redis.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;

import org.shan.spring.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

import javax.sql.DataSource;

/**
 * Created by amanda.shan on 2018/11/23.
 */
@Configuration
@ConditionalOnClass(RedisOperations.class)
public class RedisCacheConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {

        //设置序列化
        FastJsonRedisSerializer objectFastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer); // key序列化
        redisTemplate.setValueSerializer(objectFastJsonRedisSerializer); // value序列化
        redisTemplate.setHashKeySerializer(stringSerializer); // Hash key序列化
        redisTemplate.setHashValueSerializer(objectFastJsonRedisSerializer); // Hash value序列化
        redisTemplate.setBeanClassLoader(new ClassLoader() {
        });
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }


    @Bean
    public <T> RedisUtil<T> redisUtil(RedisTemplate<String, Object> redisTemplate) {
        RedisUtil<T> redisUtil = new RedisUtil<>();
        redisUtil.setRedisTemplate(redisTemplate);

        return redisUtil;
    }
}
