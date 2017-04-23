package com.merpyzf.zhihudaily.data;

import com.merpyzf.zhihudaily.data.entity.NewsBean;
import com.merpyzf.zhihudaily.data.entity.SplashBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by 春水碧于天 on 2017/4/21.
 */

public interface ZhiHuDailyApi {

    public String Base_URL = "http://news-at.zhihu.com/api/";


//    http://news-at.zhihu.com/api/7/prefetch-launch-images/1080*1920
    @GET("7/prefetch-launch-images/{image_size}")
    Observable<SplashBean> getSplashImage(@Path("image_size")String image_size);

//    http://news-at.zhihu.com/api/4/news/latest
    @GET("4/news/latest")
    Observable<NewsBean> getLatestNews();



//    http://news-at.zhihu.com/api/

    @GET("4/news/before/{news_date}")
    Observable<NewsBean>getHistoryNews(@Path("news_date")String news_date);
}
