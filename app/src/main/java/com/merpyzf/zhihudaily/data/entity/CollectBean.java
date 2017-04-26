package com.merpyzf.zhihudaily.data.entity;

/**
 * 新闻收藏对象
 * Created by 春水碧于天 on 2017/4/26.
 */

public class CollectBean {

    private int news_id;
    private String news_title;
    private String news_image;
    private String news_collect_date;

    public CollectBean(int news_id, String news_title, String news_image, String news_collect_date) {
        this.news_id = news_id;
        this.news_title = news_title;
        this.news_image = news_image;
        this.news_collect_date = news_collect_date;
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_image() {
        return news_image;
    }

    public void setNews_image(String news_image) {
        this.news_image = news_image;
    }



    public String getNews_collect_date() {
        return news_collect_date;
    }

    public void setNews_collect_date(String news_collect_date) {
        this.news_collect_date = news_collect_date;
    }
}
