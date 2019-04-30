package org.shan.spring.base.utils.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by amanda.shan on 2018/10/31.
 * 支持三种格式的时间格式转换  string->date
 * 1、long
 * 2、yyyy-MM-dd HH:mm:ss
 * 3、yyyy-MM-dd
 */
public class StringToDateConverter implements Converter<String, Date> {

    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
    private static final String shortDateFormat = "yyyy-MM-dd";

    @Override
    public Date convert(String value) {

        if(StringUtils.isEmpty(value)) {
            return null;
        }

        value = value.trim();

        try {
            if(value.contains("-")) {
                SimpleDateFormat formatter;
                if(value.contains(":")) {
                    formatter = new SimpleDateFormat(dateFormat);
                }else {
                    formatter = new SimpleDateFormat(shortDateFormat);
                }

                Date dtDate = formatter.parse(value);
                return dtDate;
            }else if(value.matches("^\\d+$")) {
                Long lDate = new Long(value);
                return new Date(lDate);
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("parser %s to Date fail", value));
        }
        throw new RuntimeException(String.format("parser %s to Date fail", value));
    }
}
