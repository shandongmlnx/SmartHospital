## 配置

### 服务端配置

```
dubbo.application.name = dubbo-provider-demo
dubbo.application.qos.enable=false

dubbo.protocol.port = 20002
dubbo.protocol.name = dubbo

# 设置注册中心地址
dubbo.registry.protocol=zookeeper
dubbo.registry.address = 192.168.1.170:2181,192.168.1.176:2181

# 不设置注册中心
dubbo.registry.address = N/A

# 设置本机IP地址
dubbo.provider.host=192.168.1.167

# 多地址支持 这样就能设置发布的本机IP地址
dubbo.config.multiple=true
```

### 消费端配置

```
dubbo.application.name = dubbo-consumer-demo
dubbo.application.qos.enable=false

# 设置注册中心地址
dubbo.registry.protocol=zookeeper
dubbo.registry.address = 192.168.1.170:2181,192.168.1.176:2181

# 不设置注册中心
dubbo.registry.address = N/A
```

## 直连

设置消费端URL
```
@Reference(version = "1.0.0", url = "dubbo://localhost:20002", timeout = 10000)
    private ITestService iTestService;
```


