package org.shan.spring.mybatis.type_handler;


import com.mlnx.common.enums.BaseCodeEnum;

/**
 * Created by amanda.shan on 2018/4/23.
 */
public class CodeEnumUtil {

    public static <E extends Enum<?> & BaseCodeEnum> E codeOf(Class<E> enumClass, String code) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getValue().equals(code)) {
                return e;
            }
        }
        return null;
    }
}
