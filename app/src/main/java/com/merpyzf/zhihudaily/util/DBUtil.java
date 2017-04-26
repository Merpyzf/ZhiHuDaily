package com.merpyzf.zhihudaily.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.merpyzf.zhihudaily.data.ZhiHuDailyApplication;
import com.merpyzf.zhihudaily.data.entity.CollectBean;

/**
 * Created by 春水碧于天 on 2017/4/26.
 */

public class DBUtil {

    private static Context context = null;

    private static SQLiteDatabase dbWrite;

    private static final String TABLE_NAME = "tab_news_collect";

    static {

        context = ZhiHuDailyApplication.getContext();

        DBHelper dbHelper = new DBHelper(context);

        dbWrite = dbHelper.getWritableDatabase();

        LogUtil.i("静态代码块执行了");
    }


    /**
     * 向数据库中插入收藏的数据
     * @param collectBean
     * @return -1表示已经存在
     */
    public static int insert(CollectBean collectBean){


        //如果不存在才能进行插入数据
        if(!isExistNews(collectBean.getNews_id())){

            ContentValues values = new ContentValues();


            values.put("news_id",collectBean.getNews_id());
            values.put("news_title",collectBean.getNews_title());
            values.put("news_image",collectBean.getNews_image());
            values.put("news_collect_date",collectBean.getNews_collect_date());

            long insert = dbWrite.insert(TABLE_NAME, null, values);

            return (int) insert;


        }else {

            return -1;

        }

    }

    /**
     * 判断要收藏的文章是否存在
     * @param news_id
     * @return true存在，false不存在
     */
    private static Boolean isExistNews(int news_id) {


        Cursor cursor = dbWrite.query(TABLE_NAME,null,"news_id=?",new String[]{news_id+""},null,null,null);


        LogUtil.i("收藏的文章==》"+cursor.getCount());

        if(cursor.getCount()>0){

            return true;
        }

        return false;

    }


    public static int delete(int newsId){


        return 0;
    };


    public static  int update(){



        return 0;
    };


    public static Cursor query(){



        return null;

    }

}
