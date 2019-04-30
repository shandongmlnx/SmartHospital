

### 数据库配置

```
spring.datasource.url=jdbc:mysql://rm-bp1745o74g3447d01o.mysql.rds.aliyuncs.com:3306/housekeeper_v2?useUnicode=true&characterEncoding=utf8
spring.datasource.username=lifeport
spring.datasource.password=Genext2017
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```


### 配置文件配置和application配置


```
#mybatis
mybatis-plus.mapper-locations=classpath:mapper/*Mapper.xml
mybatis-plus.type-aliases-package=org.shan.spring.mybatis
mybatis-plus.typeEnumsPackage=com.mlnx.product_test.entity.enums
```
需要配置@MapperScan mapper扫描
```
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.mlnx")
@ComponentScan(basePackages = {"org.shan", "com.mlnx"})
public class TestpojServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestpojServerApplication.class, args);
    }
}
```


### xml如果和mapper放在一起

```
<plugins>
    <plugin>
        <groupId>org.mybatis.generator</groupId>
        <artifactId>mybatis-generator-maven-plugin</artifactId>
        <version>1.3.2</version>
        <configuration>
            <verbose>true</verbose>
            <overwrite>true</overwrite>
        </configuration>
    </plugin>
</plugins>

<resources>
    <resource>
        <directory>src/main/java</directory>
        <includes>
            <include>**/*.xml</include>
        </includes>
    </resource>
</resources>
```

### 定义mapper

```
public interface TUser1Mapper extends BaseMapper<TUser1> {
}
```

### 定义 实体类
> BaseEntity定义啦假删除和更新创建时间字段

```
public class TUser1 extends BaseEntity{

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String phone;
```

### 自定义sql语句

1、新建mapper.xml文件
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="org.shan.spring.mybatis.TUser1Mapper" >
  <resultMap id="BaseResultMap" type="org.shan.spring.mybatis.pojo.TUser1" >
    <id column="id" property="id" jdbcType="INTEGER" />
    <result column="phone" property="phone" jdbcType="VARCHAR" />
    <result column="password" property="password" jdbcType="VARCHAR" />
    <result column="pic" property="pic" jdbcType="VARCHAR" />
    <result column="account" property="account" jdbcType="VARCHAR" />
    <result column="role_code" property="roleCode" jdbcType="VARCHAR" />
    <result column="edit_time" property="editTime" jdbcType="TIMESTAMP" />
    <result column="version" property="version" jdbcType="INTEGER" />
    <result column="status" property="status" typeHandler="org.shan.spring.mybatis.type_handler.CodeEnumTypeHandler"/>
    <result column="create_time" property="createTime" jdbcType="TIMESTAMP" />
  </resultMap>
  
  <select id = find .......
  />
</mapper>
```

2、在mapper类中新建接口方法
```
public interface TUser1Mapper extends BaseMapper<TUser1> {
    TUser1 find(String name);
}
```

### 枚举转换

1、配置枚举定义的位置

```
# 支持统配符 * 或者 ; 分割
mybatis-plus.typeEnumsPackage=com.mlnx.common.enums
```

2、定义枚举类

```

/**
    实现BaseCodeEnum接口
    实现 getValue
    实现 toString
**/

public enum PublicStatusEnum implements BaseCodeEnum{

    ACTIVE("0", "激活"),

    UNACTIVE("1", "冻结");

    private String value;

    /**
     * 描述
     */
    private String desc;

    PublicStatusEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return getValue();
    }

```
3、在类中加入枚举变量, 要加上 @EnumValue 注解

```
    @EnumValue
    private ProductType type;   //产品类型
```


### 增删改查
[条件构造器](http://mp.baomidou.com/guide/wrapper.html)

```
// 保存
TUser1 user = new TUser1();
user.setAccount("shandong3");
user.setPassword("shandong3");
user.setPhone("158245039132");
user.setRoleCode("1");
user.setCreateTime(new Date());
tUser1Mapper.insert(user);

Map<String, Object> columnMap = new HashedMap();

// 更新
user = new TUser1();
user.setCreateTime(new Date());
user.setPassword("123456");
user.setVersion(3);
tUser1Mapper.update(user, new QueryWrapper<TUser1>().eq("account", "shandong2"));

// 删除
tUser1Mapper.delete(new QueryWrapper<TUser1>().eq("account", "shandong1"));

// 查找
columnMap.put("account", "shandong3");
List<TUser1> tUser1s = tUser1Mapper.selectByMap(columnMap);
logger.info(tUser1s+"");


// 分页查找
IPage<TUser1> tUser1IPage = tUser1Mapper.selectPage(new Page<TUser1>(1, 10), new QueryWrapper<TUser1>());

logger.info(tUser1IPage+"");
```

### 多表查询使用分页

- selectUserShop 增加page参数
```
public interface TUser1Mapper extends BaseMapper<TUser1> {

    public Page<UserShop> selectUserShop(Page<UserShop> page);
}
```
- 定义xml

```
  <select id="selectUserShop" resultMap="UserShopResultMap">
    select u.id id, s.name name FROM t_user1 u, t_shop s
    WHERE u.id=s.user_id
  </select>
```

- 使用

```
IPage<UserShop> userShopIPage = tUser1Mapper.selectUserShop(new Page<UserShop>(1, 10));
```




### 乐观锁


```
// 定义version
public class TUser1 extends BaseEntity{

    @TableId(type = IdType.AUTO)
    private Integer id;

    @Version
    @TableField(exist=true, update="%s+1")
    private Integer version = 0;// 版本号默认为0
    

// 更新设置version，判断update返回值是否大于0
user = new TUser1();
user.setCreateTime(new Date());
user.setPassword("123456");
user.setVersion(3);
boolean sucess = tUser1Mapper.update(user, new QueryWrapper<TUser1>().eq("account", "shandong2"));
if(sucess){
    System.out.println("Update successfully");
}else{
    System.out.println("Update failed due to modified by others");
}
```










