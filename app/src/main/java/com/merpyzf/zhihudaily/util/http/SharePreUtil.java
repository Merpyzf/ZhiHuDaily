package com.merpyzf.zhihudaily.util.http;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 春水碧于天 on 2017/4/24.
 */

public class SharePreUtil {

    private static String key = "json";
    private static String preName = "cache";

    public static void putString(Context context,
                                 String Value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                preName, context.MODE_PRIVATE);

        sharedPreferences.edit().putString(key, Value).commit();

    }

    public static String getString(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                preName, context.MODE_PRIVATE);

        return sharedPreferences.getString(key, "null");

    }

}
