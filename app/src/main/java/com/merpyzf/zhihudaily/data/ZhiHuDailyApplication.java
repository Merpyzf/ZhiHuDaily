package com.merpyzf.zhihudaily.data;

import android.app.Application;
import android.content.Context;

/**
 * Created by 春水碧于天 on 2017/4/26.
 */

public class ZhiHuDailyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

    }

    public static Context getContext(){

        return context;
    }

}
