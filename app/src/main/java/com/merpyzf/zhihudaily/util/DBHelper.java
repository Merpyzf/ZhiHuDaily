package com.merpyzf.zhihudaily.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.version;

/**
 * Created by 春水碧于天 on 2017/4/26.
 */

public class DBHelper extends SQLiteOpenHelper {

    //需要保存的信息有： 当前新闻的id,新闻标题，新闻图片url,新闻的时间,收藏的时间

    String sql = "CREATE TABLE tab_news_collect(_id INTEGER AUTO_INCREMENT PRIMARY KEY,news_id INTEGER,news_title VARCHAR(200),news_image VARCHAR(200),news_release_date TIMESTAMP,news_collect_date TIMESTAMP)";

    public DBHelper(Context context) {
        super(context,"ZhiHuDaily.db", null, version);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
