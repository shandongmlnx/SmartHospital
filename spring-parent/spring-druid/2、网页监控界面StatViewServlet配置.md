### 主要功能

> 提供监控信息展示的html页面

> 提供监控信息的JSON API

### 配置


```

spring:
  datasource:
    druid:
      stat-view-servlet:
        enabled: true           #是否启用StatViewServlet默认值true
        url-pattern: /shan/*    #监控界面的url设置
        login-username: shan    #登录用户名
        login-password: 123456  #登入密码
        reset-enable: true      #允许清空统计数据
        allow: 128.242.127.4    #允许的ip
        deny: 128.242.127.4     #拒绝的ip

```


