
```
private MockMvc mockMvc; // 模拟MVC对象，通过MockMvcBuilders.webAppContextSetup(this.wac).build()初始化。

@Autowired
private WebApplicationContext wac; // 注入WebApplicationContext


@Before // 在测试开始前初始化工作
public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
}

@Test
public void testControll() throws Exception {
    //调用接口，传入添加的用户参数
    RequestBuilder request = MockMvcRequestBuilders.post("/record/testDate").contentType(MediaType
            .APPLICATION_FORM_URLENCODED).content("date=1540961944000");
    MvcResult mvcResult = mockMvc.perform(request).andReturn();

    log.info(mvcResult.getResponse().getContentAsString());
    
    MvcResult mvcResult = mockMvc.perform(request).andReturn() ; int status = mvcResult.getResponse().getStatus(); String content = mvcResult.getResponse().getContentAsString(); Assert.assertTrue("正确", status == 200); Assert.assertFalse("错误", status != 200); System.out.println("返回结果："+status);
}

```
