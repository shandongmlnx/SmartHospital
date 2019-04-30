
### 1、参数验证

#### 对象验证
> obj       对象  
> includeSuper  是否包含父类参数  
> notIncludes   不需要验证的参数
```
isInVailObj(Object obj, boolean includeSuper, String... notIncludes)

// 使用

if (ObjectValidUtil.isInVailObj(product, "id", "remark")) {
    return result(ExceptionMsg.ParamError);
}


```

#### 参数验证
```
 isInvalid(Object... objs)

// 使用
if (ObjectValidUtil.isInvalid(current, size)) {
    return result(ExceptionMsg.ParamError);
}
```

### 2、引入hutool-all库

[使用说明文档](http://hutool.mydoc.io/)





