### 使用方法


```
// 不带参数的get请求
RestUtils.get("http://localhost:8080/get", String.class);

// 带参数的get请求
Map<String, Object> map = new HashMap<>();
map.put("name", "shan");
map.put("age", 11);
s = RestUtils.get("http://localhost:8080/get", String.class, map);

// post 表单提交
s = RestUtils.post("http://localhost:8080/post", String.class, map);

// post json对象提交
s = RestUtils.post("http://localhost:8080/post?name=shan", String.class, "sbjdbajsbdh");
```


