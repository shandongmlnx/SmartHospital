package org.shan.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.shan.spring.druid.SpringDruidApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import javax.sql.DataSource;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDruidApplication.class)
public class SpringDruidApplicationTests {

	@Autowired
	private ApplicationContext context;

	@Test
	public void contextLoads() {

		Map<String, DataSource> dataSources = context.getBeansOfType(DataSource.class);

		System.out.println();
	}

}
