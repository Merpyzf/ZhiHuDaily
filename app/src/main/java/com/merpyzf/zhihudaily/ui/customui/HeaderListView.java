package com.merpyzf.zhihudaily.ui.customui;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.merpyzf.zhihudaily.R;
import com.merpyzf.zhihudaily.data.entity.NewsBean;
import com.merpyzf.zhihudaily.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 春水碧于天 on 2017/4/22.
 * 组合控件，带有轮播图的ListView
 */

public class HeaderListView extends ListView implements View.OnTouchListener,ViewPager.OnPageChangeListener{

    private Context context;
    @BindView(R.id.viewPager) ViewPager mViewPager;
    @BindView(R.id.ll_point) LinearLayout ll_point;

    private TextView tv_top_title;
    private ImageView iv_top_show;
    private int mPosition;

    private List<NewsBean.TopStoriesBean> mTopStories = null;
    private ArrayList<View> mTopViews = null;
    private MyPagerAdapter mPagerAdapter = null;
    private Timer mTimer;


    public HeaderListView(Context context) {
        this(context,null);
    }

    public HeaderListView(Context context, AttributeSet attrs) {

        this(context,attrs,0);
    }

    public HeaderListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        //初始化头布局
        InitHeader();

    }

    /**
     * 设置新闻数据
     * @param TopStories
     */
    public void setTopStories(List TopStories){

        this.mTopStories = TopStories;

        if(mPagerAdapter == null) {

            //初始化数据
            InitData();

        }else {

            mPagerAdapter.notifyDataSetChanged();
        }

    }

    /**
     * 初始化数据
     */
    private void InitData() {

        mTopViews = new ArrayList<View>();

        for(int i=0;i<mTopStories.size();i++){


            View view = View.inflate(context,R.layout.item_viewpager,null);

            tv_top_title = (TextView) view.findViewById(R.id.tv_top_title);

            iv_top_show = (ImageView)view.findViewById(R.id.iv_top_show);

            iv_top_show.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {


                    LogUtil.i(mPosition+"");
                    //轮播图的点击事件回调
                    mTopPagerClickListener.click(mPosition,mTopStories.get(mPosition));

                }
            });



            NewsBean.TopStoriesBean storiesBean = mTopStories.get(i);

            Glide.with(context).load(storiesBean.getImage())
                    .centerCrop()
                    .into(iv_top_show);

            tv_top_title.setText(storiesBean.getTitle());

            mTopViews.add(view);


        }


        InitPoint(mTopViews.size());


        //数据初始化完毕之后，将数据源设置到适配器

         mPagerAdapter = new MyPagerAdapter();
        mViewPager.setAdapter(mPagerAdapter);


        mViewPager.setCurrentItem(500000-(5000000%mTopStories.size()));


        startTopPagerLoop();
        mViewPager.setOnPageChangeListener(this);


    }

    /**
     * 开始轮播图循环
     */
    private void startTopPagerLoop() {


        mTimer = new Timer();

        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                Activity activity = (Activity) context;

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);

                    }
                });


            }
        },3000,3000);

    }

    /**
     * 初始化指示器小圆点
     * @param size 小圆点的个数
     */
    private void InitPoint(int size) {


        for(int i=0;i<size;i++){

            ImageView iv_gray_point = new ImageView(context);

            iv_gray_point.setImageResource(R.drawable.gray_point_shape);

            if(i==0){

                iv_gray_point.setImageResource(R.drawable.white_point_shape);
            }

            ll_point.addView(iv_gray_point);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_gray_point.getLayoutParams();

            params.width = 28;
            params.height = 28;
            params.leftMargin = 20;

        }

    }


    /**
     * 初始化listview的头布局
     */
    private void InitHeader() {

        View view = View.inflate(context, R.layout.header_listview,null);

        ButterKnife.bind(this,view);
        // TODO: 2017/4/24 根据屏幕的高度动态设置轮播图的高度，指示器的大小
        addHeaderView(view);
        mViewPager.setOnTouchListener(this);

    }

    /**
     *  当手指滑动viewPager时停止循环轮播,当手指移除之后继续进行轮播
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:

                if(mTimer!=null){

                    mTimer.cancel();
                    mTimer = null;
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if(mTimer!=null){

                    mTimer.cancel();

                    mTimer = null;
                }


                break;


            case MotionEvent.ACTION_UP:
                //当手指抬起时继续进行轮播图的滚动
                startTopPagerLoop();

                break;


        }

        return false;
    }


    int lastX = 0;
    int lastY = 0;
    /**
     * 进行轮播图左右滑动时的事件拦截
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {


        switch (ev.getAction()){

            case MotionEvent.ACTION_DOWN:

                lastX = (int) ev.getRawX();

                lastY = (int)ev.getY();

                break;

            case MotionEvent.ACTION_MOVE:

                int offsetX = (int) ev.getRawX() - lastX;
                int offsetY = (int) ev.getRawY() - lastY;
                //判读滑动的方向
                if (Math.abs(offsetX)>Math.abs(offsetY)) {

                    requestDisallowInterceptTouchEvent(true);

                } else {

                    requestDisallowInterceptTouchEvent(false);
                }


                lastX = (int) ev.getRawX();
                lastY = (int) ev.getRawY();
            case MotionEvent.ACTION_UP:

                break;
        }


        return super.dispatchTouchEvent(ev);

    }




    class MyPagerAdapter extends android.support.v4.view.PagerAdapter{


        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            int newPosition =  position%mTopStories.size();

            View view = mTopViews.get(newPosition);
            //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
            ViewParent viewParent =view.getParent();
            if (viewParent!=null){
                ViewGroup parent = (ViewGroup)viewParent;
                parent.removeView(view);
            }
            container.addView(view);




            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            //注意：不在这里进行view销毁的工作

        }
    }


    /**
     * ViewPager的滑动状态监听
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 当ViewPager页面切换时，更新指示器的状态
     * @param position
     */
    @Override
    public void onPageSelected(final int position) {


        for(int i=0;i<ll_point.getChildCount();i++){

            if(i==position%mTopViews.size()){

                ImageView point = (ImageView) ll_point.getChildAt(i);
                point.setImageResource(R.drawable.white_point_shape);
            }else {

                ImageView point = (ImageView) ll_point.getChildAt(i);
                point.setImageResource(R.drawable.gray_point_shape);
            }

        }

        mPosition = position%mTopStories.size();

    }



    @Override
    public void onPageScrollStateChanged(int state) {

    }


    /**
     * ViewPager点击的回调监听，将事件传递给外部
     */
    private TopPagerClickListener mTopPagerClickListener;

    public interface TopPagerClickListener{

        void click(int position,NewsBean.TopStoriesBean storiesBean);

    }
    public void setOnTopPagerClickListener(TopPagerClickListener topPagerClickListener){

        mTopPagerClickListener = topPagerClickListener;

    }

}
