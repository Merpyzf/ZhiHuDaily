package com.merpyzf.zhihudaily.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 春水碧于天 on 2017/4/23.
 */

public class DateUtil {

    public static Date getLastDay(Date date) {


        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(date);
        Date now = ca.getTime();
        ca.add(Calendar.DAY_OF_MONTH, -1); //月份减1
        Date lastDay = ca.getTime(); //结果
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sf.format(now));

        return lastDay;
    }

    public static String getNowDay(Date date) {


        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(date);
        Date now = ca.getTime();
        ca.add(Calendar.DAY_OF_MONTH, -1); //月份减1
        Date lastDay = ca.getTime(); //结果
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");

        return sf.format(now);
    }


}
