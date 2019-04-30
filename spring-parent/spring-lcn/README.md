

### 配置事务管理器 txManager

properties文件配置
```
lcn.tm.url = http://192.168.1.176:7000/tx/manager/
```


### 消费端

- 消费端作为事务的发起方 **@TxTransaction(isStart = true)**
- 消费端dubbo需要配置 ==dubbo.consumer.filter = transactionFilter==
```
@Service
public class TestService {

    static final String url = "";
    @Reference(url = url, timeout = 10000)
    LcnService1 lcnService1;

    @TxTransaction(isStart = true)
    @Transactional
    public void test(){
        lcnService1.test("shan");

        int i = 1/0;
    }
}
```


### 服务端

- 服务端只要添加 **@TxTransaction**
```
@Service
public class LcnService1Iml implements LcnService1 {

    Logger logger = LoggerFactory.getLogger(LcnService1Iml.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @TxTransaction
    @Transactional
    @Override
    public void test(String param) {

        jdbcTemplate.execute("insert into user(name, age) values('shan', 11)");

        logger.info("test服务被调用");
    }
}
```