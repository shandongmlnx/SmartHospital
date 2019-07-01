package com.mlnx.smart.auth;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by amanda.shan on 2019/3/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthApplication.class)
public class TestAuthApplication {

    //ObjectMapper是一个可以重复使用的对象
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build(); //构造MockMvc
    }
}
