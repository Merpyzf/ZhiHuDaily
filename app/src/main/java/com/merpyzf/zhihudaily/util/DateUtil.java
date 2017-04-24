package com.merpyzf.zhihudaily.util;

import java.text.ParseException;
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


    public static String getWeek(String date)  {
        String Week = null;
        try {
            Week = "星期";
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");//也可将此值当参数传进来
            String pTime = format.format(format.parse(date));
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(format.parse(pTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            switch(c.get(Calendar.DAY_OF_WEEK)){
                case 1:
                    Week += "日";
                    break;
                case 2:
                    Week += "一";
                    break;
                case 3:
                    Week += "二";
                    break;
                case 4:
                    Week += "三";
                    break;
                case 5:
                    Week += "四";
                    break;
                case 6:
                    Week += "五";
                    break;
                case 7:
                    Week += "六";
                    break;
                default:
                    break;
            }


            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return (month+1)+"月"+day+"日"+" "+Week;
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
        }

        return null;

    }

}
