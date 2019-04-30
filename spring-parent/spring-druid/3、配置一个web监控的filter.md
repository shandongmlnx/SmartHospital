### WebStatFilter

> 用于配置Web和Druid数据源之间的管理关联监控统计

[详细说明](https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_%E9%85%8D%E7%BD%AEWebStatFilter)

```
spring:
  datasource:
    druid:
      web-stat-filter:
        enabled: true       #一般只要设置一个即可
        
        
        exclusions
        sessionStatEnable
        sessionStatMaxCount
        principalSessionName
        principalCookieName
        profileEnable
```
