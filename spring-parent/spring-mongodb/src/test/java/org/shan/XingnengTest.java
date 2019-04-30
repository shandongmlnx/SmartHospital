package org.shan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.shan.bean.SimpleBean;
import org.shan.spring.mongodb.DuplicateKeyHandler;
import org.shan.spring.mongodb.MongodbApplication;
import org.shan.spring.mongodb.MongodbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by amanda.shan on 2019/1/30.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongodbApplication.class)
public class XingnengTest {

    Logger logger = LoggerFactory.getLogger(XingnengTest.class);

    @Test
    public void test() {

        long time = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            SimpleBean simpleBean = new SimpleBean();
            MongodbUtils.save(simpleBean);
        }
        logger.info("单个保存花费时间:" + (System.currentTimeMillis() - time));


        testMutil(5);
        testMutil(10);
        testMutil(15);
        testMutil(20);

    }

    private void testMutil(int sendCount) {
        List<SimpleBean> simpleBeans = new ArrayList<>();
        long time = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            SimpleBean simpleBean = new SimpleBean();
            simpleBeans.add(simpleBean);

            if (simpleBeans.size() >= sendCount) {
                MongodbUtils.saveBatch(simpleBeans, SimpleBean.class);
                simpleBeans.clear();
            }
        }
        MongodbUtils.saveBatch(simpleBeans, SimpleBean.class);
        simpleBeans.clear();
        logger.info(sendCount + "批量保存花费时间:" + (System.currentTimeMillis() - time));
    }

    /**
     * 批量存储例子
     */
    @Test
    public void testInsertEx() {

        Map<String, Sort.Direction> map = new HashMap<>();
        map.put("name", Sort.Direction.ASC);
        map.put("age", Sort.Direction.ASC);

//        MongodbUtils.setIndex("SimpleBean", map, true);

        long time = System.currentTimeMillis();
        List<SimpleBean> simpleBeans = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            SimpleBean simpleBean = new SimpleBean("shan" + i, i);

            simpleBeans.add(simpleBean);
        }
        simpleBeans.add(new SimpleBean("shan" + 12, 12));
        saveBatchTryDuplicateKey(simpleBeans);
    }

    /**
     * 批量存储 处理异常
     * @param simpleBeans
     */
    private void saveBatchTryDuplicateKey(List<SimpleBean> simpleBeans) {
        MongodbUtils.saveBatchTryDuplicateKey(simpleBeans, SimpleBean.class, new DuplicateKeyHandler() {
            @Override
            public void process(List<String> keys) {

                System.err.println(keys);

                if (keys.size() == 2) {
                    int temp = -1;
                    for (int i = 0; i < simpleBeans.size(); ++i) {
                        SimpleBean simpleBean = simpleBeans.get(i);
                        if (simpleBean.getName().equals(keys.get(0))
                                && (simpleBean.getAge() + "").equals(keys.get(1))) {
                            temp = i;
                            break;
                        }
                    }
                    if (temp != -1 && temp < simpleBeans.size()-1 ){
                        for (int i = 0; i <= temp; i++) {
                            simpleBeans.remove(i);
                        }
                        saveBatchTryDuplicateKey(simpleBeans);
                    }
                }
            }
        });
    }
}
