package org.shan.spring.zookeeper.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by amanda.shan on 2018/12/10.
 */
@Configuration
public class ZookeeperConfig {

    @Configuration("zookeeperProper")
    @ConfigurationProperties("mlnx.zookeeper")
    public static class ZookeeperProper {
        // 连接地址
        // 192.168.1.170:2181,192.168.1.174:2181,192.168.1.188:2181 单个
        // 192.168.1.170:2181   集群
        private String connectionInfo;
        // 会话超时时间
        private Integer sessionTimeoutMs;
        // 连接超时时间
        private Integer connectionTimeoutMs;

        public String getConnectionInfo() {
            return connectionInfo;
        }

        public void setConnectionInfo(String connectionInfo) {
            this.connectionInfo = connectionInfo;
        }

        public Integer getSessionTimeoutMs() {
            return sessionTimeoutMs;
        }

        public void setSessionTimeoutMs(Integer sessionTimeoutMs) {
            this.sessionTimeoutMs = sessionTimeoutMs;
        }

        public Integer getConnectionTimeoutMs() {
            return connectionTimeoutMs;
        }

        public void setConnectionTimeoutMs(Integer connectionTimeoutMs) {
            this.connectionTimeoutMs = connectionTimeoutMs;
        }
    }


    @Bean
    public CuratorFramework client(ZookeeperProper zookeeperProper) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client =
                CuratorFrameworkFactory.builder()
                        .connectString(zookeeperProper.connectionInfo)
                        .sessionTimeoutMs(zookeeperProper.sessionTimeoutMs == null ? 5000 : zookeeperProper.sessionTimeoutMs)
                        .connectionTimeoutMs(zookeeperProper.connectionTimeoutMs == null ? 3000 : zookeeperProper.connectionTimeoutMs)
                        .retryPolicy(retryPolicy)
                        .build();
        client.start();


        return client;
    }
}
