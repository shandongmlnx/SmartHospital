### 打包说明

1. 在最外面目录下运行clean
```
mvn clean
```

2. 在最外面目录下运行install
```
// 打包跳过测试
mvn install -Dmaven.test.skip=true
```

3. 打包consumer, 在device-consumer目录下运行package
```
// 打包跳过测试
mvn package -Dmaven.test.skip=true
```

4. 打包服务，在device_dubbo_service目录下运行package
```
// 打包跳过测试
mvn package -Dmaven.test.skip=true
```

5. 设置版本号， 在最外层目录下运行下面的命令，版本号自己定义
```
mvn versions:set -DnewVersion=1.1
```





