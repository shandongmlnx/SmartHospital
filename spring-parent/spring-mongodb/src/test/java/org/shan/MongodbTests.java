package org.shan;

import com.mongodb.client.gridfs.model.GridFSFile;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.shan.bean.MongoBean;
import org.shan.bean.Person;
import org.shan.bean.SimpleBean;
import org.shan.spring.mongodb.MongodbApplication;
import org.shan.spring.mongodb.MongodbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongodbApplication.class)
public class MongodbTests {

    Logger logger = LoggerFactory.getLogger(MongodbTests.class);

    @Autowired
    ApplicationContext context;

    @Test
    public void test() {

        MongoBean mongoBean = new MongoBean();
        mongoBean.setDate(new Date());
        mongoBean.setPerson(new Person("单栋", 30));

        List<MongoBean> mongoBeans = new ArrayList<>();
        mongoBeans.add(mongoBean);
        mongoBeans.add(mongoBean);
        mongoBeans.add(mongoBean);

        MongodbUtils.saveBatch(mongoBeans, MongoBean.class);
    }

    @Test
    public void testInsert() {
        List<SimpleBean> mongoBeans = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {

        }

        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setName("shan");
        simpleBean.setAge(11);
        mongoBeans.add(simpleBean);

        simpleBean = new SimpleBean();
        simpleBean.setName("shan1");
        simpleBean.setAge(12);
        mongoBeans.add(simpleBean);

        try {
            MongodbUtils.saveBatch(mongoBeans, SimpleBean.class);
        } finally {
            for (SimpleBean bean : mongoBeans) {
                System.out.println(bean.toString());
            }
        }

    }

    @Test
    public void testExist() {

        Criteria criteria = Criteria.where("aLong").is(1000);

        Query query = new Query();

        MongodbUtils.mongodbUtils.mongoTemplate.exists(query, SimpleBean.class);
    }

    @Test
    public void testUpdate() {

        Criteria criteria = Criteria.where("aLong").is(1000);
        Query query = Query.query(criteria);


        Update update = new Update();
        update.unset("anInt");
        update.set("key", "calue");
        update.push("key", "value");
        update.pullAll("key", new Object[]{"", ""});

        MongodbUtils.updateFirst(criteria, update, "MongoBean");
    }


    @Autowired
    public MongoTemplate mongoTemplate;

    @Test
    public void testAggregate(){

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project(),
                Aggregation.limit(1));

        AggregationResults<MongoBean> aggregateResults = mongoTemplate.aggregate(aggregation, "MongoBean", MongoBean.class);

        System.out.println(aggregateResults);

    }

    @Autowired
    GridFsTemplate gridFsTemplate;

    GridFsOperations gridFsOperations;

    @Test
    public void testGridFs() throws IOException {

        try {
            gridFsTemplate.store(new FileInputStream(new File("e:\\20180328-device_server.log")), "device_server.log");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        GridFSFile fsFile = gridFsTemplate.findOne(new Query());
        System.out.println(fsFile);

        GridFsResource gridFsResource = gridFsTemplate.getResource(fsFile.getFilename());

        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            outputStream = new FileOutputStream(new File("e:\\test.log"));
            inputStream = gridFsResource.getInputStream();
            
            byte[] bytes = new byte[255*1024];
            int len;
            
            while ((len = inputStream.read(bytes)) != -1 ){
                outputStream.write(bytes, 0, len);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null){
                inputStream.close();
            }
            if (outputStream != null){
                outputStream.close();
            }
        }


//        List<GridFSDBFile> result = gridFsTemplate.find(new Query().addCriteria(Criteria.where("filename").is
//                (fileName)));
    }

    public static void main(String[] args) {

        System.out.println("123\r\n456");
        Pattern compile = Pattern.compile("123\r\n456", Pattern.MULTILINE);
        Matcher matcher = compile.matcher("^123$\r\n^456$");
        System.out.println(matcher.matches());

    }

    static class TestJson {

        ObjectId id = new ObjectId();

    }

}
