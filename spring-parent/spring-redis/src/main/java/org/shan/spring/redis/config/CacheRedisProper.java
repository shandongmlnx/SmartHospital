package org.shan.spring.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Created by amanda.shan on 2019/3/27.
 */
@Data
@ConfigurationProperties("mlnx.cache.redis")
public class CacheRedisProper {

    // 超时时间设置
    private Duration timeToLive = new Duration();

    // 是否加前缀
    private String keyPrefix;

    @Data
    public static class Duration {
        private long seconds = 0;
        private int nanos = 0;

        public java.time.Duration duration(){
            return java.time.Duration.ofSeconds(seconds, nanos);
        }
    }

}
