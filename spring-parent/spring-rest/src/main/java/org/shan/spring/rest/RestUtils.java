package org.shan.spring.rest;

import com.alibaba.fastjson.JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import javax.annotation.PostConstruct;

/**
 * Created by amanda.shan on 2018/9/29.
 */
@Component
public class RestUtils {

    public static RestUtils restUtils;

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        restUtils = this;
    }

    public static RestTemplate getRestTemplate() {
        return restUtils.restTemplate;
    }

    public static Head buildHead() {
        return new Head();
    }

    public static class Head {
        private HttpHeaders httpHeaders;

        public Head() {
            httpHeaders = new HttpHeaders();
        }

        public HttpHeaders build() {

            return httpHeaders;
        }

        public Head cJson() {
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            httpHeaders.setContentType(type);
            return this;
        }

        public Head cForm() {
            MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
            httpHeaders.setContentType(type);
            return this;
        }

        public Head aJson() {
            httpHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());
            return this;
        }
    }

    /**
     * get 不带参数
     *
     * @param url
     * @param responseType
     * @param <T>
     * @return
     */
    public static <T> T get(String url, Class<T> responseType) {
        return get(url, responseType, null);
    }

    /**
     * get 带参数
     *
     * @param url
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public static <T> T get(String url, Class<T> responseType, Map<String, ?> uriVariables) {
        return get(url, responseType, null, buildHead().aJson().build());
    }

    /**
     * get 自定义
     *
     * @param url
     * @param responseType
     * @param uriVariables
     * @param httpHeaders
     * @param <T>
     * @return
     */
    public static <T> T get(String url, Class<T> responseType, Map<String, ?> uriVariables, HttpHeaders httpHeaders) {
        //利用容器实现数据封装，发送
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, httpHeaders);

        ResponseEntity<T> response = null;
        if (uriVariables != null) {
            response = restUtils.restTemplate.exchange(url, HttpMethod.GET, requestEntity,
                    responseType, uriVariables);
        } else {
            response = restUtils.restTemplate.exchange(url, HttpMethod.GET, requestEntity,
                    responseType);
        }

        return response.getBody();
    }


    /**
     * post json提交
     *
     * @param url
     * @param responseType
     * @param reqParam
     * @param <T>
     * @return
     */
    public static <T> T post(String url, Class<T> responseType, Object reqParam) {
        return post(url, responseType, reqParam, null, buildHead().aJson().cJson().build());
    }

    /**
     * post 表单提交
     *
     * @param url
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public static <T> T post(String url, Class<T> responseType, Map<String, ?> uriVariables) {

        StringBuilder builder = new StringBuilder();
        for (String key : uriVariables.keySet()) {
            builder.append(key + "=" + uriVariables.get(key) + "&");
        }
        if (builder.length() > 0) {
            builder.delete(builder.length() - 1, builder.length());
        }

        return post(url, responseType, builder.toString(), uriVariables, buildHead().aJson().cForm().build
                ());
    }

    /**
     * post 自定义
     *
     * @param url
     * @param responseType
     * @param reqParam
     * @param uriVariables
     * @param httpHeaders
     * @param <T>
     * @return
     */
    public static <T> T post(String url, Class<T> responseType, Object reqParam, Map<String, ?> uriVariables,
                             HttpHeaders httpHeaders) {

        //利用容器实现数据封装，发送
        HttpEntity<String> entity = new HttpEntity<String>(reqParam == null ? null : (reqParam.getClass().equals
                (String.class)? reqParam.toString() : JSON.toJSONString(reqParam)), httpHeaders);

        ResponseEntity<T> response = null;
        if (uriVariables != null) {
            response = restUtils.restTemplate.postForEntity(url, entity, responseType, uriVariables);
        } else {
            response = restUtils.restTemplate.postForEntity(url, entity, responseType);
        }

        return response.getBody();
    }


}
