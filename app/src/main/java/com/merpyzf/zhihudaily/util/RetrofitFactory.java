package com.merpyzf.zhihudaily.util;

/**
 * Created by 春水碧于天 on 2017/4/21.
 */

public class RetrofitFactory {

    //线程同步的锁
    private static final Object monitor = new Object();
    //网络请求时是否打印日志
    public static boolean isDebug = true;

    private static ZhiHuDailyApi sZhiHuDailySingleton= null;

    public static ZhiHuDailyApi getZhiHUDailyServiceInstance(){


        if(sZhiHuDailySingleton == null){


            synchronized (monitor){

                if(sZhiHuDailySingleton == null){

                    sZhiHuDailySingleton = new RetrofitClient().getZhiHuDailyService();

                }



            }

        }


        return sZhiHuDailySingleton;

    }






}
