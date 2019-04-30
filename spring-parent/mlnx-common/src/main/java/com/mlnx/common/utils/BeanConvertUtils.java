package com.mlnx.common.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortConverter;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 2016/5/4.
 */
public class BeanConvertUtils {

    static {
        // 在封装之前 注册转换器
        ConvertUtils.register(new DateTimeConverter(), Date.class);
        ConvertUtils.register(new DateTimeConverter(), java.sql.Date.class);
        ConvertUtils.register(new LongConverter(null), Long.class);
        ConvertUtils.register(new ShortConverter(null), Short.class);
        ConvertUtils.register(new IntegerConverter(null), Integer.class);
        ConvertUtils.register(new DoubleConverter(null), Double.class);
        ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
    }

    public static Map<String, Object> toMap(Object obj) {
        return toMap(obj, true, new HashSet<String>());
    }

    /**
     * 获取对象参数map
     *
     * @param obj
     * @param all            是否把全部null的数据也加入
     * @param notIncludeKeys
     * @return
     */
    public static Map<String, Object> toMap(Object obj, boolean all, Set<String> notIncludeKeys) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                /*过滤class属性  */
                if (!key.equals("class")) {
                    //*得到property对应的getter方法*/
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    if (!notIncludeKeys.contains(key) && (all || value != null)) {
                        map.put(key, value);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将javabean转换为Map
     * @param obj
     * @return
     */
    public static Map<String, Object> bean2Map(Object obj) {
        try {
            Map<String, Object> map = BeanUtils.describe(obj);
            map.remove("class");
            return map;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Map转换为javabean
     * @param map
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T map2Bean(Map<String, Object> map, Class<T> clazz) {
        if (map == null || clazz == null) {
            return null;
        }
        T bean = null;
        try {
            bean = clazz.newInstance();
            BeanUtils.populate(bean, map);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 对象间的属性值拷贝
     *
     * @param dest 目标对象
     * @param src  源对象
     */
//    public static void copyProperties(Object dest, Object src) {
//        if (src == null || dest == null) {
//            return;
//        }
//        try {
//            BeanUtils.copyProperties(dest, src);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        }
//    }

}
