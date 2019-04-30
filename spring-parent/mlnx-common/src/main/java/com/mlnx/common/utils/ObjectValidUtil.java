package com.mlnx.common.utils;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/6/23.
 */
public class ObjectValidUtil {

    public static boolean isInVailObj(Object obj) {
        return isInVailObj(obj, false);
    }

    public static boolean isInVailObj(Object obj, String... notIncludes) {
        return isInVailObj(obj, false, notIncludes);
    }

    /**
     * 验证参数是否有效
     * @param obj       对象
     * @param includeSuper  是否包含父类参数
     * @param notIncludes   不需要验证的参数
     * @return
     */
    public static boolean isInVailObj(Object obj, boolean includeSuper, String... notIncludes) {
        if (obj == null) {
            return true;
        }
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (!key.equals("class")) {

                    if (includeSuper || (!includeSuper && isInclude(fields, key))){
                        if (notIncludes == null || !isInclude(notIncludes, key)) {

                            Object o = property.getReadMethod().invoke(obj);
                            if (o == null) {
                                return true;
                            } else if (property.getPropertyType().equals(String.class) && StringUtils
                                    .isEmpty(o.toString())) {
                                return true;
                            }
//                            System.out.println(key);
                        }
                    }
                }
//                System.out.println(key + " " + property.getPropertyType() + "   " + property
//                        .getReadMethod().invoke(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean isInclude(String[] notIncludes, String key) {
        for (int i = 0; i < notIncludes.length; i++) {
            if (notIncludes[i].equals(key)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isInclude(Field[] fields, String key) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().equals(key)) {
                return true;
            }
        }
        return false;
    }



    public static boolean isValid(Object... objs) {
        if (objs == null || objs.length < 1) {
            return false;
        }

        for (Object obj : objs) {
            if (obj instanceof Short) {
                if (isNull(obj)) {
                    return false;
                }
            } else if (obj instanceof Integer) {
                if (isInvalidInteger((Integer) obj)) {
                    return false;
                }
            } else if (obj instanceof Long) {
                if (isInvalidLong((Long) obj)) {
                    return false;
                }
            } else if (obj instanceof String) {
                if (isInvalidString(obj.toString())) {
                    return false;
                }
            } else if (obj instanceof List) {
                if (CollectionUtils.isEmpty((List) obj)) {
                    return false;
                }
            } else if (obj instanceof Map) {
                if (isNull(obj) || ((Map) obj).isEmpty()) {
                    return false;
                }
            } else {
                if (isNull(obj)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isInvalid(Object... objs) {
        return !isValid(objs);
    }

    /**
     * 判断是否为有效Short值
     *
     * @param num
     * @return
     */
    public static boolean isValidShort(Short num) {
        if (num == null) {
            return false;
        }
        return true;
    }

    public static boolean isInvalidShort(Short num) {
        return !isValidShort(num);
    }

    /**
     * 判断是否为有效Integer值
     *
     * @param num
     * @return
     */
    public static boolean isValidInteger(Integer num) {
        if (num == null) {
            return false;
        }
        return true;
    }

    public static boolean isInvalidInteger(Integer num) {
        return !isValidInteger(num);
    }

    /**
     * 判断是否为有效Long值
     *
     * @param num
     * @return
     */
    public static boolean isValidLong(Long num) {
        if (num == null) {
            return false;
        }
        return true;
    }

    public static boolean isInvalidLong(Long num) {
        return !isValidLong(num);
    }

    /**
     * 判断是否为有效BigDecimal值
     *
     * @param num
     * @return
     */
    public static boolean isValidBigDecimal(BigDecimal num) {
        if (num == null || num.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        return true;
    }

    public static boolean isInvalidBigDecimal(BigDecimal num) {
        return !isValidBigDecimal(num);
    }

    /**
     * 判断是否为有效String值
     *
     * @param str
     * @return
     */
    public static boolean isValidString(String str) {
        return StringUtils.isNotBlank(str);
    }

    public static boolean isInvalidString(String str) {
        return StringUtils.isBlank(str);
    }

    public static boolean isNull(Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public static boolean isValidCurPage(Integer curPage) {
        if (curPage == null) {
            return false;
        }
        if (curPage.compareTo(1) < 0) {
            return false;
        }
        return true;
    }

    public static boolean isValidCurPage(Long curPage) {
        if (curPage == null) {
            return false;
        }
        if (curPage.compareTo(1L) < 0) {
            return false;
        }
        return true;
    }

    public static boolean isInvalidCurPage(Integer curPage) {
        return !isValidCurPage(curPage);
    }

    public static boolean isInvalidCurPage(Long curPage) {
        return !isValidCurPage(curPage);
    }

    public static boolean isValidViewNumber(Integer viewNumber) {
        if (viewNumber == null) {
            return false;
        }
        if (viewNumber.compareTo(0) <= 0) {
            return false;
        }
        return true;
    }

    public static boolean isValidViewNumber(Long viewNumber) {
        if (viewNumber == null) {
            return false;
        }
        if (viewNumber.compareTo(0L) <= 0) {
            return false;
        }
        return true;
    }

    public static boolean isInvalidViewNumber(Integer viewNumber) {
        return !isValidViewNumber(viewNumber);
    }

    public static boolean isInvalidViewNumber(Long viewNumber) {
        return !isValidViewNumber(viewNumber);
    }

    public static boolean isValidLimit(Integer limit) {
        if (limit == null) {
            return false;
        }
        if (limit.compareTo(0) <= 0) {
            return false;
        }
        return true;
    }

    public static boolean isInvalidLimit(Integer limit) {
        return !isValidLimit(limit);
    }

    public static boolean isAllNull(Object... objs) {
        if (objs == null || objs.length < 1) {
            return true;
        }
        int nullcount = 0;
        for (Object obj : objs) {
            if (isNull(obj)) {
                nullcount++;
            }
        }
        return objs.length == nullcount;
    }

}
