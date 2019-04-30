### pom

```
 <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

### 配置

```
#mongodb
# 数据库名字
spring.data.mongodb.database = test
# 地址
spring.data.mongodb.host = 127.0.0.1
# 端口
spring.data.mongodb.port = 27017
# 用户名
spring.data.mongodb.username=
# 密码
spring.data.mongodb.password=
```

## MongodbUtils 使用

### 注解配置model

- Document 用来设置表名
- Id用来设置ID， 可以手动设置也可以不设置，不设置会默认填充
```
@Document(collection = "mongodbTestModel")
public class MongodbTestModel {
    @Id
    private String mid;
```

### 设置索引


```
Map<String, Sort.Direction> map = new HashMap<>();
map.put("name", Sort.Direction.ASC);

MongodbUtils.setIndex("mongodbTestModel", map, false);
```


### 保存

```
// 表名注解设置啦
MongodbUtils.save(mo);

// 表名动态代码设置
MongodbUtils.save(mo, "test23");
```

### 查找

##### 基本查找

```
MongodbTestModel obj = new MongodbTestModel();
String[] findKeys = {"name"};
String[] findValues = {"tat"};
String collectionName = "test23";
List<? extends Object> find = MongodbUtils.find(obj.getClass(), findKeys, findValues, collectionName);
```

##### 指定query查找


```
Criteria criteria = Criteria.where("patientId").is(patientId);
criteria.and("startTime").lte(startTime);
Query query = Query.query(criteria);
query.limit(limit).with(new Sort(Sort.Direction.DESC, "startTime"));
query.fields().include("startTime").include("patientId").include("deviceId").include("deviceType");

return MongodbUtils.find(Map.class, query, MlnxDataMongoConfig.ECG_COLLECTIONNAME);
```

### 更新
- 复杂更新
```
Criteria criteria = Criteria.where("aLong").is(1000);
Query query = Query.query(criteria);

Update update = new Update();

// 删除anInt
update.unset("anInt");
// 更新 key的值
update.set("key", "calue");
// 数组插入 一个值
update.push("key", "value");
// 数组插入多个值
update.pullAll("key", new Object[]{"", ""});

// 更新一条数据
MongodbUtils.updateFirst(query, update, "MongoBean");
```









