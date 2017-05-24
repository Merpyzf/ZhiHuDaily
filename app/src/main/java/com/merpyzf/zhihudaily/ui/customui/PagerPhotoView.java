package com.merpyzf.zhihudaily.ui.customui;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.merpyzf.zhihudaily.R;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by 春水碧于天 on 2017/5/24.
 */

public class PagerPhotoView extends ViewPager {

    private List<String> imagesUrl = null;
    private Context mContext;
    Paint mPaint = null;


    public PagerPhotoView(Context context) {
        this(context,null);
        mContext = context;
    }

    public PagerPhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;


    }


    public void setImagesUrl(List<String> imagesUrl,int index) {

        this.imagesUrl = imagesUrl;

        this.setAdapter(new myPagerAdapter());

        this.setCurrentItem(index);

    }




    class myPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return imagesUrl.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = View.inflate(mContext, R.layout.show_photo_layout, null);

            PhotoView mPhotoView = (PhotoView) view.findViewById(R.id.photoView);

            mPhotoView.setZoomable(true);

            Glide.with(mContext).load(imagesUrl.get(position))
                    .priority(Priority.HIGH)
                    .into(mPhotoView);


            container.addView(view);


            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);

        }
    }


}
