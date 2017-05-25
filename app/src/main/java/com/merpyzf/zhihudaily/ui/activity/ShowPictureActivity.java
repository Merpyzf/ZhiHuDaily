package com.merpyzf.zhihudaily.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.merpyzf.zhihudaily.R;
import com.merpyzf.zhihudaily.ui.customui.PhotoPagerView;
import com.merpyzf.zhihudaily.util.ImageUtil;
import com.merpyzf.zhihudaily.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 展示网页中的图片
 *
 * @author 春水碧于天
 */
public class ShowPictureActivity extends AppCompatActivity {

    private Context context;
    private String image_url = null;
    private String page_image = null;
    @BindView(R.id.pagerPhotoView)
    PhotoPagerView pagerPhotoView;

    private String downUrl = null;
    private List<String> pageImages;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Boolean mIsHidden = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        ButterKnife.bind(this);

        mAppBar.setAlpha(0.7f);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        context = this;
        image_url = getIntent().getStringExtra("image_url"); //这个是手指点击的那张图片的url
        page_image = getIntent().getStringExtra("page_image"); //这个是当前页面中的所有图片

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

        pagerPhotoView.setOnViewTapListener(new PhotoPagerView.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {

                LogUtil.i("被轻触了");
                hideOrShowToolbar();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_show_picture, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                onBackPressed();
                finish();
                break;

            case R.id.action_save:


                ImageUtil.SaveImage(downUrl, ShowPictureActivity.this);

                break;

        }


        return true;

    }

    protected void hideOrShowToolbar() {
        mAppBar.animate()
                .translationY(mIsHidden ? 0 : -mAppBar.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsHidden = !mIsHidden;
    }


}
