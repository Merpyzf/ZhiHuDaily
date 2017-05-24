package com.merpyzf.zhihudaily.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.merpyzf.zhihudaily.R;
import com.merpyzf.zhihudaily.ui.customui.PagerPhotoView;
import com.merpyzf.zhihudaily.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class ShowImageActivity extends AppCompatActivity {

    private Context context;
    private String image_url = null;
    private String page_image = null;
    private PagerPhotoView pagerPhotoView;
    private FrameLayout fl_down = null;
    private String downUrl = null;
    private List<String> pageImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_image);
        context = this;
        image_url = getIntent().getStringExtra("image_url"); //这个是手指点击的那张图片的url
        page_image = getIntent().getStringExtra("page_image"); //这个是当前页面中的所有图片

        initUI();


        String[] split = page_image.split(" ");

        pageImages = new ArrayList<>();
        for (int i = 1; i < split.length; i++) {

            pageImages.add(split[i]);
        }

        downUrl = pageImages.get(0);

        int indexOf = pageImages.indexOf(image_url);

        pagerPhotoView.setImagesUrl(pageImages, indexOf);


        pagerPhotoView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {

                downUrl = pageImages.get(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });



        fl_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ImageUtil.SaveImage(downUrl,ShowImageActivity.this);


            }
        });


    }

    private void initUI() {

        pagerPhotoView = (PagerPhotoView) findViewById(R.id.pagerPhotoView);
        fl_down = (FrameLayout) findViewById(R.id.fl_downImage);


    }
}
