package com.mlnx.agv.tp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zzb
 * @create 2019/4/15 12:41
 */
public class DateUtil {
    public  static int getHour(Long time){
        Date date1=new Date(time);
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        c.setTime(date1);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
//        System.out.println(year + "/" + month + "/" + date + " " +hour + ":" +minute + ":" + second);
    return hour;
    }
    public static void main(String[] args) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String time="1991/03/22 22:24";
        Long d=0L;
        try {
            d=sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date1=new Date(d);
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
//        c.setTime(date1);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);

        System.out.println(year + "/" + month + "/" + date + " " +hour + ":" +minute + ":" + second);
    }
}
