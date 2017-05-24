package com.merpyzf.zhihudaily.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileOutputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import static com.merpyzf.zhihudaily.util.LogUtil.i;

/**
 * Created by 春水碧于天 on 2017/5/24.
 */

public class ImageUtil {

    static String dir = null;
    static String image_name = null;

    public  static void SaveImage(final String image_url, final Activity context) {


        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {


            dir = Environment.getExternalStorageDirectory().toString() + "/ZhiHuDaily/Images";


            Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> e) throws Exception {


                    File DirPath = new File(dir);

                    //如果没有这个文件夹就去创建
                    if (!DirPath.exists()) {
                        i("创建的文件:" + DirPath.getPath());
                        boolean b = DirPath.mkdirs();
                        i("创建结果:" + b);
                    }

                    e.onNext(image_url);
                    e.onComplete();


                }
            }).filter(new Predicate<String>() {


                @Override
                public boolean test(String s) throws Exception {


                    String[] split = image_url.split("/");

                    image_name = split[split.length - 1];
                    Boolean exists = isImageExists(new File(dir), image_name);


                    if (exists) {

                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                ToastUtil.show(context, "以前已经保存过啦，亲");
                            }
                        });
                    }


                    i("图片是否存在:" + exists);
                    return !exists;


                }
            })
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {

                            Bitmap bitmap = Glide.with(context)
                                    .load(image_url)
                                    .asBitmap()
                                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                    .get();


                            File image_file = new File(dir, image_name);

                            boolean isSave = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(image_file));


                            if (isSave) {


                                context.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        ToastUtil.show(context, "保存成功");
                                    }

                                    //通知图库更新

                                });
                                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                        Uri.fromFile(image_file)));


                            } else {


                                context.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        ToastUtil.show(context, "保存失败");
                                    }
                                });

                            }


                        }
                    });
        } else {


            ToastUtil.show(context, "外部存储设备未挂载！");

        }


    }

    /**
     * 判断图片是否存在在当前文件夹
     *
     * @param dirPath   图片存储的目录
     * @param imageName 文件名
     * @return true(文件存在)/false（文件不存在）
     */
    public static Boolean isImageExists(File dirPath, String imageName) {

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


}
