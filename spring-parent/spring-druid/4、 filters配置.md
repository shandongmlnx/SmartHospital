
> Druid内置提供一个StatFilter，用于统计监控信息。

[说明文档](https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_StatFilter)

### 配置例子
```
spring:
  datasource:
    druid:
    # 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
      filters: wall,stat
      filter:
        stat:
        # 通过connectProperties属性来打开mergeSql功能；慢SQL记录
          merge-sql: true
          slow-sql-millis: 10
          logSlowSql: true
```

## stat

### SQL合并配置

> merge-sql: true

```
select * from t where id = 1
select * from t where id = 2
select * from t where id = 3

//简化为

select * from t where id = ?
```

### 慢SQL记录


```
slow-sql-millis: 10
logSlowSql: true
```

上述代表sql执行时间超过10ms的进行记录，如果sql超过10ms会有以下打印：


```
2018-09-25 13:14:56.149 ERROR 8168 --- [io-8081-exec-10] c.alibaba.druid.filter.stat.StatFilter   : slow sql 14 millis. select
     
'true' as QUERYID,
 
id, phone, password, pic, account, role_code

from t_user
 
   
 WHERE (  phone = ? )["15824503913"]
```




