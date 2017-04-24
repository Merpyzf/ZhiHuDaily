package com.merpyzf.zhihudaily.util;

import android.content.Context;

import com.google.gson.Gson;
import com.merpyzf.zhihudaily.data.entity.NewsBean;
import com.merpyzf.zhihudaily.util.http.SharePreUtil;

/**
 * Created by 春水碧于天 on 2017/4/24.
 * 将json数据缓存到sp文件中
 */

public class CacheUtil {

    public static void cacheLatestNews(Context context,NewsBean newsBean){

        Gson gson = new Gson();

        String json = gson.toJson(newsBean);

        SharePreUtil.putString(context,json);
    }


    public static NewsBean getCacheLatestNews(Context context){

        String json = SharePreUtil.getString(context);

        if(json!=null && !json.equals("null")) {

            Gson gson = new Gson();

            return gson.fromJson(json, NewsBean.class);

        }
        return null;
    }




}
