
[DruidDataSource配置属性列表](https://github.com/alibaba/druid/wiki/DruidDataSource%E9%85%8D%E7%BD%AE%E5%B1%9E%E6%80%A7%E5%88%97%E8%A1%A8)

![image](https://camo.githubusercontent.com/b9ab6274b6c81498554fa61cd253bcedcca29e2f/687474703a2f2f7037666372713265342e626b742e636c6f7564646e2e636f6d2f32303138303531302d3131303233324c2e6a7067)

[常见问题](https://github.com/alibaba/druid/wiki/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98)

### spring boot 集成

#### pom引入


```
<!-- druid 引入 -->
<dependency>
	<groupId>com.alibaba</groupId>
	<artifactId>druid-spring-boot-starter</artifactId>
	<version>1.1.10</version>
</dependency>
```

#### 基础配置


```
spring:
  datasource:
    druid:
      url: jdbc:mysql://rm-bp1745o74g3447d01o.mysql.rds.aliyuncs.com:3306/housekeeper_v2?useUnicode=true&characterEncoding=utf8
      username: lifeport
      password: Genext2017
      driver-class-name: com.mysql.jdbc.Driver
      db-type: mysql
      
      #配置监控统计拦截的filters
      filters: stat
      filter:
        stat:
          #SQL合并
          merge-sql: true

          # 慢SQL记录
          slow-sql-millis: 3000
          logSlowSql: true

      #监控servlet配置
      stat-view-servlet:
        enabled: true
        login-username: shan
        login-password: 123456
        reset-enable: true

      #监控filter拦截配置
      web-stat-filter:
        enabled: true
```
#### 高级配置

[参考例子](https://github.com/alibaba/druid/wiki/DruidDataSource%E9%85%8D%E7%BD%AE)
```
spring:
  datasource:
    druid:
      url: jdbc:mysql://rm-2ze2xc0xo8z5u6g6jo.mysql.rds.aliyuncs.com:3306/ecg_ui_release?useUnicode=true&characterEncoding=utf8
      username: mlnx_dbadmin
      password: medgen2011NB!
      driver-class-name: com.mysql.jdbc.Driver
      db-type: mysql
      # 配置初始化大小、最小、最大
      initial-size: 2
      max-active: 30
      min-idle: 2
      #配置获取连接等待超时的时间
      max-wait: 60000
      #配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
      time-between-eviction-runs-millis: 2000
      #配置一个连接在池中最小生存的时间，单位是毫秒
      min-evictable-idle-time-millis: 600000
      max-evictable-idle-time-millis: 900000

      validation-query: select 1
      test-while-idle: true
      test-on-borrow: false
      test-on-return: false
#      pool-prepared-statements: false
#      use-global-data-source-stat: true
      #配置监控统计拦截的filters
      filters: stat
      filter:
        stat:
          #SQL合并
          merge-sql: true

          # 慢SQL记录
          slow-sql-millis: 3000
          logSlowSql: true

      #监控servlet配置
      stat-view-servlet:
        enabled: true
        login-username: shan
        login-password: 123456
        reset-enable: true

      #监控filter拦截配置
      web-stat-filter:
        enabled: true
```

#### 访问 web

> http://127.0.0.1:8081/druid/index.html



