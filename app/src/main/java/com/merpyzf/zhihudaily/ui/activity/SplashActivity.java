package com.merpyzf.zhihudaily.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.merpyzf.zhihudaily.MainActivity;
import com.merpyzf.zhihudaily.R;
import com.merpyzf.zhihudaily.data.entity.SplashBean;
import com.merpyzf.zhihudaily.util.LogUtil;
import com.merpyzf.zhihudaily.util.ToastUtil;
import com.merpyzf.zhihudaily.util.http.RetrofitFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import static com.merpyzf.zhihudaily.util.LogUtil.i;

/**
 * Splash页面
 *
 * @author 春水碧于天
 */
public class SplashActivity extends AppCompatActivity {


    @BindView(R.id.iv_splash)
    ImageView iv_splash;
    @BindView(R.id.fl_download)
    FrameLayout fl_download_image;
    private Context context;
    private String mSplashImageUrl = null;
    private static final int REQUEST_WRITESTORAGE_CODE = 0;
    private String mSplashImageName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);
        context = this;

        /**
         * 请求知乎日报api接口获取splash页面的json数据解析并展示图片到imageview
         */
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
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String splash_image_url) {


                        mSplashImageUrl = splash_image_url;

                        Glide.with(context)
                                .load(splash_image_url)
                                .diskCacheStrategy(DiskCacheStrategy.ALL) //设置图片全尺寸缓存
                                .error(R.drawable.img_error)
                                .centerCrop()
                                .into(iv_splash);

                        setScaleAnim(iv_splash);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                       LogUtil.i("出错");

//                        mSplashImageName
                        LogUtil.i("图片名称:"+mSplashImageName);

                        ToastUtil.show(context,"你已经进入了没有网络的异次元");

                        startActivity(new Intent(context, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    /**
     * 添加缩放动画，当动画结束之后进行跳转
     *
     * @param iv_splash
     */
    private void setScaleAnim(ImageView iv_splash) {

        Animation scaleAnimation = new ScaleAnimation(1f, 1.1f, 1f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(3000);
        scaleAnimation.setFillBefore(true);
        scaleAnimation.setFillAfter(true);
        iv_splash.startAnimation(scaleAnimation);

        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                //动画结束之后进行跳转到主页面

                startActivity(new Intent(context, MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


    @OnClick(R.id.fl_download)
    public void downloadImage(View v) {

        /**
         * 判断当前设备的版本信息
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //判断当前权限是否被授予
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                saveImage();

            } else {


                //没有被授予去申请权限

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITESTORAGE_CODE);

            }


        } else {

            //6.0以下的版本直接操作
            saveImage();

        }


    }

    /**
     * 保存splash页面展示的图片：
     * 1.创建文件要下载的目录
     * 2.判断所下载的文件是否已经存在，如果不存在再去下载
     * 3.下载结束之后，刷新数据库
     */
    private void saveImage() {


        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            Observable.create(new ObservableOnSubscribe<File>() {
                @Override
                public void subscribe(ObservableEmitter<File> e) throws Exception {


                    String dir = Environment.getExternalStorageDirectory().toString() + "/ZhiHuDaily/Images";

                    File DirPath = new File(dir);

                    //如果没有这个文件夹就去创建
                    if (!DirPath.exists()) {
                        i("创建的文件:" + DirPath.getPath());
                        boolean b = DirPath.mkdirs();
                        i("创建结果:" + b);
                    }

                    //将当前存储图片的路径作为事件发送给观察者
                    e.onNext(DirPath);
                    e.onComplete();


                }
                //事件过滤，如果要下载的图片存在就将事件过滤掉
            }).filter(new Predicate<File>() {
                @Override
                public boolean test(File file) throws Exception {

                    String[] split = mSplashImageUrl.split("/");

                    mSplashImageName = split[split.length - 1];

                    //如果文件不存在才将事件发送给Observer
                    Boolean exists = isImageExists(file, mSplashImageName);

                    if (exists) {

                        ToastUtil.show(context, "图片已经保存");
                    }

                    return !exists;
                }
                //事件变换，将存储文件的路径和要存储的文件名 创建一个file继续发送给Observer
            }).map(new Function<File, File>() {
                @Override
                public File apply(File dirFile) throws Exception {

                    return new File(dirFile, mSplashImageName);
                }
            })
                    .observeOn(Schedulers.io())
                    .subscribe(new Observer<File>() {
                        @Override
                        public void onSubscribe(Disposable d) {


                        }

                        @Override
                        public void onNext(File value) {

                            /**
                             * 保存图片
                             */
                            try {
                                Bitmap bitmap = Glide.with(context)
                                        .load(mSplashImageUrl)
                                        .asBitmap()
                                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                        .get();

                                boolean isSave = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(value));

                                if (isSave) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            ToastUtil.show(context, "保存成功");
                                        }
                                    });

                                    //通知图库更新
                                    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                            Uri.fromFile(value)));

                                } else {

                                    ToastUtil.show(context, "保存失败");
                                }

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } finally {
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {


            i("外部存储设备没有被挂载");
        }

    }

    /**
     * 判断图片是否存在在当前文件夹
     *
     * @param dirPath   图片存储的目录
     * @param imageName 文件名
     * @return true(文件存在)/false（文件不存在）
     */
    private Boolean isImageExists(File dirPath, String imageName) {

        File[] files = dirPath.listFiles();

        if (files.length > 0) {

            for (File file : files) {

                if (file.getName().equals(imageName)) {

                    return true;

                }

            }
        }


        return false;

    }

    /**
     * 权限申请结果的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case REQUEST_WRITESTORAGE_CODE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    saveImage();

                } else {

                    ToastUtil.show(context, "亲，您拒绝了文件读写的权限/(ㄒoㄒ)/~~");

                }

                return;

        }


    }
}
