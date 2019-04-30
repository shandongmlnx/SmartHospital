package org.shan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.shan.spring.mongodb.MongodbApplication;
import org.shan.spring.mongodb.MongodbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by amanda.shan on 2019/1/28.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongodbApplication.class)
public class AggregationTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Document(collection = "TagCount")
    static class TagCount {
        String tag;
        int n;

        public TagCount(String tag, int n) {
            this.tag = tag;
            this.n = n;
        }

        public TagCount() {
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public int getN() {
            return n;
        }

        public void setN(int n) {
            this.n = n;
        }

        @Override
        public String toString() {
            return "TagCount{" +
                    "tag='" + tag + '\'' +
                    ", n=" + n +
                    '}';
        }
    }

    @Test
    public void insert(){

        String[] tags = {"tag1", "tag2", "tag3", "tag4", "tag5", "tag6", "tag7"};
        Random random = new Random();
        List<TagCount> tagCounts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TagCount tagCount = new TagCount(tags[random.nextInt(tags.length)], random.nextInt(10));
            tagCounts.add(tagCount);
        }

        MongodbUtils.saveBatch(tagCounts, TagCount.class);
    }

    @Test
    public void find(){
        Aggregation agg = newAggregation(
                project("tag"),
                unwind("tag"),
                group("tag").count().as("n"),
                project("n").and("tag").previousOperation(),
                sort(DESC, "n")
        );

        System.out.println(agg.toString());

        AggregationResults<TagCount> results = MongodbUtils.aggregate(agg, "TagCount", TagCount.class);
        List<TagCount> tagCount = results.getMappedResults();

        System.out.println(tagCount);
    }
}
