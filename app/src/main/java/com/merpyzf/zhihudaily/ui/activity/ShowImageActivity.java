package com.merpyzf.zhihudaily.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.target.Target;
import com.merpyzf.zhihudaily.R;
import com.merpyzf.zhihudaily.util.LogUtil;
import com.merpyzf.zhihudaily.util.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import uk.co.senab.photoview.PhotoView;

import static com.merpyzf.zhihudaily.util.LogUtil.i;

public class ShowImageActivity extends AppCompatActivity {

    private PhotoView mPhotoView;
    private LinearLayout View;
    private Context context;
    private String image_name;
    private String dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        context = this;
        final String image_url = getIntent().getStringExtra("image_url");

        mPhotoView = (PhotoView) findViewById(R.id.photoView);
        View = (LinearLayout) findViewById(R.id.linearlayout_show_image);

        mPhotoView.setZoomable(true);

        Glide.with(this).load(image_url)

                .priority(Priority.HIGH)
                .into(mPhotoView);


        /*final Snackbar snackbar = Snackbar.make(View, "要保存图片吗?😝", Toast.LENGTH_LONG);
        snackbar.show();
        snackbar.setAction("要要要", new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(SaveImage(image_url)){

                    ToastUtil.show(context,"保存成功");

                }else {

                    ToastUtil.show(context,"保存失败");
                }



            }
        });
*/

        mPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                SaveImage(image_url);


                return false;
            }
        });

    }

    private boolean SaveImage(final String image_url) {


        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

                dir = Environment.getExternalStorageDirectory().toString() + "/ZhiHuDaily/Images";

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

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            ToastUtil.show(context, "以前已经保存过啦，亲");
                        }
                    });
                }


                LogUtil.i("图片是否存在:" + exists);

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


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    ToastUtil.show(context, "保存成功");
                                }

                                //通知图库更新

                            });
                            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                    Uri.fromFile(image_file)));


                        } else {


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    ToastUtil.show(context, "保存失败");
                                }
                            });

                        }


                    }
                });


        return false;
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
}
