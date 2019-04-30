
### 1、Response
```
// 不传参数
return new Response();

// 传入实现BaseExceptionMsg的枚举对象
return new Response(exceptionMsg);

// 带数据的返回
return new ResponseData(obj);

// 由使用者来实现异常枚举
public enum ExceptionMsg implements BaseExceptionMsg {
    SUCCESS("0000", "操作成功"),
    FAILED("999999", "操作失败"),
    ParamError("000001", "参数错误！"),

    //  失败(02开始标示DB操作相关错误码)
    DB_FAIL("000100", "数据库操作失败"),

    OutinRecordServiceERROR1("000200", "库存不足"),
```

### 2、BaseController
```
public class BaseController {

    protected MyLog logger = MyLog.getLog(this.getClass());

    protected Response result() {
        return new Response();
    }

    protected Response result(Object obj) {
        return new ResponseData(obj);
    }

    protected Response result(BaseExceptionMsg exceptionMsg) {
        return new Response(exceptionMsg);
    }

}
```

### 3、日志拦截

> 实现下面的类，定义包的位置；然后就可以使用注解 @SysLog控制打印
```
@Aspect
@Component
public class SysLogAspect extends CommonLogAspect {

    @Pointcut("execution(public * org.shan.spring.base..*(..))")
    public void cutController() {
    }
}


// 使用打印接口
@RestController
@RequestMapping(value = "/TestController")
public class TestController {

    private MyLog logger = MyLog.getLog(this.getClass());

    @SysLog
    @ApiOperation(value = "测试1", notes = "")
    @GetMapping("/test")
    public String test() {

        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");

        return "sucess";
    }

```

### 3、date时间

- 请求参数中的date格式化
请求参数里面有date变量，系统支持三种默认格式化：  
1、yyyy-MM-dd HH:mm:ss  
2、yyyy-MM-dd  
3、long型时间数据

也就是说如果发送的是"2018-10-30" "10000000"等格式都是支持的 

- 响应数据里面有date：

```
@MlnxJsonFormat(datePattern = "yyyy-MM-dd")
@PostMapping("/getOutinRecordPage")
@ApiOperation(value = "分页获取产品", notes = "")
public Object getOutinRecordPage(@RequestParam Long current, @RequestParam Long size, @RequestParam Integer
        productId) {

    if (ObjectValidUtil.isInvalid(current, size, productId)) {
        return result(ExceptionMsg.ParamError);
    }
    return result(outinRecordService.getOutinRecordPage(current, size, productId));
}
```

返回值必须设置成object，另外可以改变datePattern来设置序列化后的时间格式类型


