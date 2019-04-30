package org.shan.spring.redis.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Created by amanda.shan on 2018/11/21.
 */
@Configuration
@EnableCaching
@EnableConfigurationProperties(CacheRedisProper.class)
public class CacheConfig {

    public static final String MCManager = "mapCacheManager";

    public static final String RCManager = "redisCacheManager";

    @Bean("keyGenerator")
    public KeyGenerator keyGenerator() {
        // 设置自动key的生成规则，配置spring boot的注解，进行方法级别的缓存
        // 使用：进行分割，可以很多显示出层级关系
        // 这里其实就是new了一个KeyGenerator对象，只是这是lambda表达式的写法，我感觉很好用，大家感兴趣可以去了解下
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(":");
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(":" + String.valueOf(obj));
            }
            String rsToUse = String.valueOf(sb);
            return rsToUse;
        };
    }

    @Bean(MCManager)
    public CacheManager mapCacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        return cacheManager;
    }

    @ConditionalOnClass(RedisOperations.class)
    @Bean(RCManager)
    @Primary
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory, CacheRedisProper cacheRedisProper) {

        RedisSerializer redisSerializer = new JdkSerializationRedisSerializer();

        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .entryTtl(cacheRedisProper.getTimeToLive().duration());

        if (!StringUtils.isEmpty(cacheRedisProper.getKeyPrefix())){
            configuration.prefixKeysWith(cacheRedisProper.getKeyPrefix()+":");
        }

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(configuration).build();
    }
}
