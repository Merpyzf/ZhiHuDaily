package com.merpyzf.zhihudaily.util.http;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.merpyzf.zhihudaily.data.ZhiHuDailyApi;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by 春水碧于天 on 2017/4/21.
 */

public class RetrofitClient {

    final ZhiHuDailyApi zhiHuDailyService;


     RetrofitClient() {

        Log.i("wk","执行了");

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

        if(RetrofitFactory.isDebug){

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient.addInterceptor(logging);

        }

        //设置连接超时的时间
        okHttpClient.connectTimeout(10, TimeUnit.SECONDS);

        OkHttpClient client = okHttpClient.build();


        Retrofit.Builder builder = new Retrofit.Builder();

        builder.baseUrl(ZhiHuDailyApi.Base_URL)
                .client(client)
                //指定事件发送的线程为io线程
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        zhiHuDailyService = retrofit.create(ZhiHuDailyApi.class);


    }

    public ZhiHuDailyApi getZhiHuDailyService(){

        return zhiHuDailyService;
    }

}
