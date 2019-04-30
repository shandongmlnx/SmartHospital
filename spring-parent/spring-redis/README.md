
## spring cache

### 1、注解使用
#### @Cacheable

根据请求参数，对结果进行缓存

```

@Cacheable
(value="person", key="#a0+'xx'")
public Person getPerson(Integer id){
    return cacheMapper.getPerson(id);
}
```

#### @CachePut 既调用方法，又更新缓存数据；同步更新缓存

1）、先调用目标方法
2）、将目标方法的结果缓存起来


```
@CachePut(key="#person.id+'xx'")
public Person updatePerson(Person person){
    cacheMapper.updatePerson(person);
    int i = 1/0;

    return person;
}
```

#### @CacheEvict删除数据


```
// 清除某个数据 根据key
@CacheEvict(key = "#a0+'xx'")
public void clear(Integer id){

}

// 清除所有数据
@CacheEvict(allEntries=true)
public void clearAll(){

}
```
### 2、配置缓存过期时间

```
#秒
spring.cache.couchbase.expiration.seconds=60
#毫秒
spring.cache.couchbase.expiration.nanos=0
```

### 3、缓存切换

使用的是redis缓存：
```
<dependency>
    <groupId>org.shan.spring</groupId>
    <artifactId>spring-cache</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

排除redis，使用默认的本地内存缓存：
```
<dependency>
    <groupId>org.shan.spring</groupId>
    <artifactId>spring-cache</artifactId>
    <version>1.0-SNAPSHOT</version>

    <!--<exclusions>-->
        <!--<exclusion>-->
            <!--<groupId>org.springframework.boot</groupId>-->
            <!--<artifactId>spring-boot-starter-data-redis</artifactId>-->
        <!--</exclusion>-->
    <!--</exclusions>-->
</dependency>
```
配置指定：
```$xslt

//本地内存缓存
@CacheConfig(cacheManager = "simpleCacheManager")
public class CacheService {

//redis缓存
@CacheConfig(cacheManager = "redisCacheManager")
public class CacheService {
```


## redis


### 配置参数说明

```
# Redis_config
# Redis数据库索引（默认为0）
spring.redis.database=3
# Redis服务器地址
spring.redis.host=127.0.0.1
# Redis服务器连接端口
spring.redis.port=6379
# Redis服务器连接密码（默认为空）
spring.redis.password=
# 连接超时时间（毫秒）
spring.redis.timeout=3600
# 连接池最大连接数（使用负值表示没有限制）
spring.redis.lettuce.pool.max-active=8
# 连接池最大阻塞等待时间（使用负值表示没有限制）
spring.redis.lettuce.pool.max-wait=-1
# 连接池中的最大空闲连接
spring.redis.lettuce.pool.max-idle=0
```

### redisUtils使用

> 序列化方式默认已经配置成 KEY为String序列化，value为json序列化


使用例子


```

@Autowired
RedisUtil<Person> redisUtil;

User user = new User();
user.setAge(10);
user.setName("单栋");

List<User> users = new ArrayList<>();
users.add(user);
users.add(user);
users.add(user);
users.add(user);


redisUtil.set("user", user);
redisUtil.set("users", users);
redisUtil.hset("class:user", "1", user);

// 序列化和反序列化都是json数据
// 自动反序列化
user = redisUtil.getObject(redisUtil.get("user"), User.class);
log.info(user.toString());

users = redisUtil.getObjectList(redisUtil.get("users"), User.class);
log.info(users+"");
```

