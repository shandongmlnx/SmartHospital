package com.mlnx.common.utils;

/**
 * Created by amanda.shan on 2019/3/29.
 */
public class BeanUtils<T> extends org.springframework.beans.BeanUtils {

    public static <T> T copy(Object source, Class<T> clazz) {

        T t = instantiateClass(clazz);
        copyProperties(source, t);
        return t;
    }
}
