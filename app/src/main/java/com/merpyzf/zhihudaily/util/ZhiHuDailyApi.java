package com.merpyzf.zhihudaily.util;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by 春水碧于天 on 2017/4/21.
 */

public interface ZhiHuDailyApi {

    public String Base_URL = "http://news-at.zhihu.com/api/";


//    http://news-at.zhihu.com/api/7/prefetch-launch-images/1080*1920
    @GET("7/prefetch-launch-images/{image_size}")
    Observable<ResponseBody> getSplashImage(@Path("image_size")String image_size);





}