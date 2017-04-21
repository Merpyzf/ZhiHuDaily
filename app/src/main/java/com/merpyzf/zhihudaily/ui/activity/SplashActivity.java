package com.merpyzf.zhihudaily.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.merpyzf.zhihudaily.R;
import com.merpyzf.zhihudaily.data.entity.SplashBean;
import com.merpyzf.zhihudaily.util.RetrofitFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Splash页面
 *
 * @author 春水碧于天
 */
public class SplashActivity extends AppCompatActivity {

    private ImageView iv_splash;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        context = this;
        InitUI();

        RetrofitFactory.getZhiHUDailyServiceInstance()
                .getSplashImage("1900*1080")
                .map(new Function<SplashBean, String>() {
                    @Override
                    public String apply(SplashBean splashBean) throws Exception {

                        //将Bean对象中的url取出，然后将事件发出
                        String splash_image_url = splashBean.getCreatives().get(0).getUrl();
                        return splash_image_url;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String splash_image_url) throws Exception {


                        Log.i("wk", "闪屏页url:" + splash_image_url + "当前线程:" + Thread.currentThread().getName());

                        Glide.with(context)
                                .load(splash_image_url)
                                .diskCacheStrategy(DiskCacheStrategy.ALL) //设置图片全尺寸缓存
                                .error(R.drawable.img_error)
                                .centerCrop()
                                .into(iv_splash);

                        setScaleAnim(iv_splash);


                    }
                });


    }

    /**
     * 添加缩放动画，当动画结束之后进行跳转
     * @param iv_splash
     */
    private void setScaleAnim(ImageView iv_splash) {

        Animation scaleAnimation = new ScaleAnimation(1f, 1.1f, 1f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(3000);
        scaleAnimation.setFillBefore(true);
        scaleAnimation.setFillAfter(true);
        iv_splash.startAnimation(scaleAnimation);


    }

    private void InitUI() {

        iv_splash = (ImageView) findViewById(R.id.iv_splash);
    }
}
