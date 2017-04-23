package com.merpyzf.zhihudaily;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.merpyzf.zhihudaily.data.entity.NewsBean;
import com.merpyzf.zhihudaily.ui.adapter.LvShowNewsAdapter;
import com.merpyzf.zhihudaily.ui.customui.HeaderListView;
import com.merpyzf.zhihudaily.util.DateUtil;
import com.merpyzf.zhihudaily.util.LogUtil;
import com.merpyzf.zhihudaily.util.http.RetrofitFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.dl_left) DrawerLayout mDrawerLayout;
    @BindView(R.id.header_listview) HeaderListView mHeaderListView;

    private ActionBarDrawerToggle mDrawerToggle;
    private LvShowNewsAdapter mAdapter;
    private String TitleName;
    private Context context;
    private Date mDate = new Date(System.currentTimeMillis());
    private NewsBean mNesBean;
    private boolean isLoading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("知乎日报");

        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        RetrofitFactory.getZhiHUDailyServiceInstance()
                .getLatestNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewsBean>() {
                    @Override
                    public void accept(NewsBean newsBean) throws Exception {

                        mNesBean = newsBean;


                        List<NewsBean.TopStoriesBean> top_stories = newsBean.getTop_stories();

                        mNesBean.getStories().get(0).setDate(newsBean.getDate());

                        mAdapter = new LvShowNewsAdapter(context,mNesBean);

                        mHeaderListView.setAdapter(mAdapter);

                        mHeaderListView.setTopStories(top_stories);



                    }
                });





        /**
         * 设置toolbar的标题,根据新闻的日期
         */
        mHeaderListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {


            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


                LinearLayout ll = null;

                if (firstVisibleItem >= 1) {

                    if (firstVisibleItem > visibleItemCount && visibleItemCount > 0) {

                        ll = (LinearLayout) mHeaderListView.getChildAt((firstVisibleItem) % visibleItemCount);

//                        LogUtil.i("firstVisibleItem==>" + firstVisibleItem + "visibleItemCount==>" + visibleItemCount + "mHeaderListViewCount==>" + mHeaderListView.getChildCount() + "\n" + "index==>" + (firstVisibleItem-1) % visibleItemCount);

                    } else {

//                        LogUtil.i("firstVisibleItem==>" + firstVisibleItem + "visibleItemCount==>" + visibleItemCount + "mHeaderListViewCount==>" + mHeaderListView.getChildCount());
                        ll = (LinearLayout) mHeaderListView.getChildAt(firstVisibleItem - 1);

                    }


                    if (ll == null) {

//                        LogUtil.i("ll为空");


                    } else {


//                    LogUtil.i("孩子的个数:"+ll.getChildCount());

                        TextView textView = (TextView) ll.getChildAt(0);




                        if(!textView.getText().toString().equals("")){

                            TitleName = textView.getText().toString();

                            getSupportActionBar().setTitle(textView.getText().toString());
                        }

                        LogUtil.i("textView中的值:" + textView.getText().toString());


                    }


                }else {


                    getSupportActionBar().setTitle("知乎日报");

                }


//                LogUtil.i("firstVisibleItem==>"+firstVisibleItem+"visibleItemCount==>"+visibleItemCount+"totalItemCount==>"+totalItemCount);
                /**
                 *
                 * 当ListView滑动到最底部进行加载过去的新闻数据
                 *
                 */
                if(firstVisibleItem+visibleItemCount == totalItemCount) {


                    //进行加载前一天的知乎新闻


                    if (isLoading) {

                        isLoading = false;



                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
                        String StrDate = sf.format(mDate);

//                        LogUtil.i("作为参数传递的日期:"+StrDate);

                        RetrofitFactory.getZhiHUDailyServiceInstance()
                                .getHistoryNews(StrDate)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<NewsBean>() {
                                    @Override
                                    public void accept(NewsBean newsBean) throws Exception {



                                        LogUtil.i("获取新闻的日期:"+newsBean.getDate());

                                        mNesBean.setDate(newsBean.getDate());
                                        newsBean.getStories().get(0).setDate(newsBean.getDate());
                                        mNesBean.getStories().addAll(newsBean.getStories());

                                        mAdapter.notifyDataSetChanged();




                                    }
                                });


                        mDate = DateUtil.getLastDay(mDate);

                    }
                }else
                {


                        isLoading = true;

                }


            }
        });


        mHeaderListView.setOnTopPagerClickListener(new HeaderListView.TopPagerClickListener() {
            @Override
            public void click(int position, NewsBean.TopStoriesBean storiesBean) {

                LogUtil.i("position:"+position+"新闻id:"+storiesBean.getId());

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main,menu);

        return super.onCreateOptionsMenu(menu);
    }

    Date lastData = new Date(System.currentTimeMillis());
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:

                if(mDrawerLayout.isDrawerOpen(Gravity.START)){

                    mDrawerLayout.closeDrawer(Gravity.START);
                }else {

                    mDrawerLayout.openDrawer(Gravity.START);
                }



                lastData = DateUtil.getLastDay(lastData);


                break;

        }


        return true;

    }


}
