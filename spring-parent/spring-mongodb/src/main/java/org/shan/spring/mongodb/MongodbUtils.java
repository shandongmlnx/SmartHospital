package org.shan.spring.mongodb;

import com.mongodb.client.result.UpdateResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexInfo;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

/**
 * Created by amanda.shan on 2018/9/29.
 */
@Component
@Order(1)
public class MongodbUtils {

    private static Logger logger = LoggerFactory.getLogger(MongodbUtils.class);

    public static MongodbUtils mongodbUtils;

    @PostConstruct
    public void init() {
        mongodbUtils = this;
        mongodbUtils.mongoTemplate = this.mongoTemplate;
    }

    @Autowired
    public MongoTemplate mongoTemplate;

    /**
     * 设置索引
     *
     * @param collectionName 表名
     * @param map            索引集合
     * @param unique         索引是否可以相同
     */
    public static void setIndex(String collectionName, Map<String, Sort.Direction> map, boolean unique) {

        Index index = new Index();
        for (String key : map.keySet()) {
            index.on(key, map.get(key));
        }
        if (unique) {
            index.unique();
        }
        mongodbUtils.mongoTemplate.indexOps(collectionName).ensureIndex(index);

        List<IndexInfo> indexInfos = mongodbUtils.mongoTemplate.indexOps(collectionName).getIndexInfo();
        logger.info(collectionName + " 索引：" + indexInfos);
    }

    /**
     * 保存数据对象，集合为数据对象中@Document 注解所配置的collection
     *
     * @param obj 数据对象
     */
    public static void save(Object obj) {

        mongodbUtils.mongoTemplate.save(obj);
    }

    /**
     * 指定集合保存数据对象
     *
     * @param obj            数据对象
     * @param collectionName 集合名
     */
    public static void save(Object obj, String collectionName) {

        mongodbUtils.mongoTemplate.save(obj, collectionName);
    }

    /**
     * 批量插入
     *
     * @param batchToSave
     */
    public static void saveBatch(Collection<? extends Object> batchToSave, Class<?> entityClass) {
        mongodbUtils.mongoTemplate.insert(batchToSave, entityClass);
    }

    /**
     * 批量插入
     *
     * @param batchToSave
     * @param collectionName 文档名字
     */
    public static void saveBatch(Collection<? extends Object> batchToSave, String collectionName) {
        mongodbUtils.mongoTemplate.insert(batchToSave, collectionName);
    }

    /**
     * 批量保存并处理重复键问题
     * @param batchToSave
     * @param collectionName
     * @param handler
     */
    public static void saveBatchTryDuplicateKey(Collection<? extends Object> batchToSave, String collectionName,
                                                DuplicateKeyHandler handler){
        try {
            saveBatch(batchToSave, collectionName);
        }catch (DuplicateKeyException e){
            processDuplicateKey(e, handler);
        }
    }

    /**
     * 批量保存并处理重复键问题
     * @param batchToSave
     * @param entityClass
     * @param handler
     */
    public static void saveBatchTryDuplicateKey(Collection<? extends Object> batchToSave, Class<?> entityClass,
                                                DuplicateKeyHandler handler) {
        try {
            saveBatch(batchToSave, entityClass);
        }catch (DuplicateKeyException e){
            processDuplicateKey(e, handler);
        }
    }

    /**
     * 处理DuplicateKey异常
     * @param e
     * @param handler
     */
    private static void processDuplicateKey(DuplicateKeyException e, DuplicateKeyHandler handler){
        String s = e.getMessage();
        String s1 = s.substring(s.indexOf("dup key: "));
        String s2 = s1.substring(s1.indexOf("{") + 1, s1.indexOf("}"));
        String[] split = s2.split(":");
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            if (!StringUtils.isEmpty(split[i].trim())) {
                keys.add(split[i].trim().replace("\"", "").replace(",", ""));
            }
        }
        if (handler != null){
            handler.process(keys);
        }
    }

    /**
     * 根据数据对象中的id删除数据，集合为数据对象中@Document 注解所配置的collection
     *
     * @param obj 数据对象
     */
    public static void remove(Object obj) {

        mongodbUtils.mongoTemplate.remove(obj);
    }

    /**
     * 指定集合 根据数据对象中的id删除数据
     *
     * @param obj            数据对象
     * @param collectionName 集合名
     */
    public static void remove(Object obj, String collectionName) {

        mongodbUtils.mongoTemplate.remove(obj, collectionName);
    }

    /**
     * 根据key，value到指定集合删除数据
     *
     * @param key            键
     * @param value          值
     * @param collectionName 集合名
     */
    public static void removeByKey(String key, Object value, String collectionName) {

        Criteria criteria = Criteria.where(key).is(value);
        criteria.and(key).is(value);
        Query query = Query.query(criteria);
        mongodbUtils.mongoTemplate.remove(query, collectionName);
    }


    /**
     * 指定集合 修改数据，且仅修改找到的第一条数据
     *
     * @param accordingKey   修改条件 key
     * @param accordingValue 修改条件 value
     * @param updateKeys     修改内容 key数组
     * @param updateValues   修改内容 value数组
     * @param collectionName 集合名
     */
    public static UpdateResult updateFirst(String accordingKey, Object accordingValue, String[] updateKeys, Object[]
            updateValues, String collectionName) {

        Criteria criteria = Criteria.where(accordingKey).is(accordingValue);
        Update update = new Update();
        for (int i = 0; i < updateKeys.length; i++) {
            update.set(updateKeys[i], updateValues[i]);
        }
        return updateFirst(criteria, update, collectionName);
    }

    public static UpdateResult updateFirst(Criteria criteria, Update update, String collectionName) {

        Query query = Query.query(criteria);

        return mongodbUtils.mongoTemplate.updateFirst(query, update, collectionName);
    }

    /**
     * 如果存在就更新，不存在就插入
     * @param criteria
     * @param update
     * @param collectionName
     * @return
     */
    public static UpdateResult upsert(Criteria criteria, Update update, String collectionName) {

        Query query = Query.query(criteria);
        return mongodbUtils.mongoTemplate.upsert(query, update, collectionName);
    }

    /**
     * 指定集合 修改数据，且修改所找到的所有数据
     *
     * @param accordingKey   修改条件 key
     * @param accordingValue 修改条件 value
     * @param updateKeys     修改内容 key数组
     * @param updateValues   修改内容 value数组
     * @param collectionName 集合名
     */
    public static UpdateResult updateMulti(String accordingKey, Object accordingValue, String[] updateKeys, Object[]
            updateValues, String collectionName) {

        Criteria criteria = Criteria.where(accordingKey).is(accordingValue);
        Update update = new Update();
        for (int i = 0; i < updateKeys.length; i++) {
            update.set(updateKeys[i], updateValues[i]);
        }
        return updateMulti(criteria, update, collectionName);
    }

    public static UpdateResult updateMulti(Criteria criteria, Update update, String collectionName) {
        Query query = Query.query(criteria);
        return mongodbUtils.mongoTemplate.updateMulti(query, update, collectionName);
    }

    /**
     * 根据条件查询出所有结果集 集合为数据对象中@Document 注解所配置的collection
     *
     * @param entityClass 数据对象
     * @param findKeys    查询条件 key
     * @param findValues  查询条件 value
     * @return
     */
    public static <T> List<T> find(Class<T> entityClass, String[] findKeys, Object[] findValues) {

        return find(entityClass, findKeys, findValues, null);
    }

    /**
     * 指定集合 根据条件查询出所有结果集 并排倒序
     *
     * @param entityClass    数据对象
     * @param findKeys       查询条件 key
     * @param findValues     查询条件 value
     * @param collectionName 集合名
     * @return
     */
    public static <T> List<T> find(Class<T> entityClass, String[] findKeys, Object[] findValues, String
            collectionName) {

        Criteria criteria = null;
        for (int i = 0; i < findKeys.length; i++) {
            if (i == 0) {
                criteria = Criteria.where(findKeys[i]).is(findValues[i]);
            } else {
                criteria.and(findKeys[i]).is(findValues[i]);
            }
        }
        return find(entityClass, criteria, collectionName);
    }

    /**
     * 根据条件查找
     *
     * @param entityClass
     * @param criteria
     * @param collectionName
     * @param <T>
     * @return
     */
    public static <T> List<T> find(Class<T> entityClass, Criteria criteria, String
            collectionName) {

        Query query = null;
        if (criteria != null) {
            query = Query.query(criteria);
        } else {
            query = new Query();
        }
        if (collectionName == null) {
            return mongodbUtils.mongoTemplate.find(query, entityClass);
        } else {
            return mongodbUtils.mongoTemplate.find(query, entityClass, collectionName);
        }
    }

    /**
     * 根据query查找
     *
     * @param entityClass
     * @param query
     * @param collectionName
     * @param <T>
     * @return
     */
    public static <T> List<T> find(Class<T> entityClass, Query query, String
            collectionName) {

        if (collectionName == null) {
            return mongodbUtils.mongoTemplate.find(query, entityClass);
        } else {
            return mongodbUtils.mongoTemplate.find(query, entityClass, collectionName);
        }
    }

    /**
     * 根据条件查询出符合的第一条数据 集合为数据对象中 @Document 注解所配置的collection
     *
     * @param entityClass 数据对象
     * @param findKeys    查询条件 key
     * @param findValues  查询条件 value
     * @return
     */
    public static <T> T findOne(Class<T> entityClass, String[] findKeys, Object[] findValues) {
        return findOne(entityClass, findKeys, findValues, null);
    }

    /**
     * 指定集合 根据条件查询出符合的第一条数据
     *
     * @param entityClass    数据对象
     * @param findKeys       查询条件 key
     * @param findValues     查询条件 value
     * @param collectionName 集合名
     * @return
     */
    public static <T> T findOne(Class<T> entityClass, String[] findKeys, Object[] findValues, String collectionName) {

        Criteria criteria = null;
        for (int i = 0; i < findKeys.length; i++) {
            if (i == 0) {
                criteria = Criteria.where(findKeys[i]).is(findValues[i]);
            } else {
                criteria.and(findKeys[i]).is(findValues[i]);
            }
        }
        Query query = Query.query(criteria);

        if (collectionName == null) {
            return mongodbUtils.mongoTemplate.findOne(query, entityClass);
        } else {
            return mongodbUtils.mongoTemplate.findOne(query, entityClass, collectionName);
        }
    }

    /**
     * 查询出所有结果集 集合为数据对象中 @Document 注解所配置的collection
     *
     * @param entityClass 数据对象class
     * @return
     */
    public static <T> List<T> findAll(Class<T> entityClass) {

        List<T> resultList = mongodbUtils.mongoTemplate.findAll(entityClass);
        return resultList;
    }

    /**
     * 指定集合 查询出所有结果集
     *
     * @param entityClass    数据对象
     * @param collectionName 集合名
     * @return
     */
    public static <T> List<T> findAll(Class<T> entityClass, String collectionName) {

        List<T> resultList = mongodbUtils.mongoTemplate.findAll(entityClass, collectionName);
        return resultList;
    }

    /**
     * 聚合操作
     * @param aggregation   操作聚合的操作符有哪些
     * @param collectionName    文档名称
     * @param outputType    // 输出格式化类
     * @param <O>
     * @return
     */
    public static <O> AggregationResults<O> aggregate(Aggregation aggregation, String collectionName, Class<O>
            outputType){
        return mongodbUtils.mongoTemplate.aggregate(aggregation, collectionName, outputType);
    }
}
