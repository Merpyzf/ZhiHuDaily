package com.merpyzf.zhihudaily;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.merpyzf.zhihudaily.data.entity.NewsBean;
import com.merpyzf.zhihudaily.ui.activity.ContentActivity;
import com.merpyzf.zhihudaily.ui.adapter.LvShowNewsAdapter;
import com.merpyzf.zhihudaily.ui.customui.HeaderListView;
import com.merpyzf.zhihudaily.util.CacheUtil;
import com.merpyzf.zhihudaily.util.DateUtil;
import com.merpyzf.zhihudaily.util.LogUtil;
import com.merpyzf.zhihudaily.util.http.RetrofitFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity  {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.dl_left)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.header_listview)
    HeaderListView mHeaderListView;

    private ActionBarDrawerToggle mDrawerToggle;
    private LvShowNewsAdapter mAdapter;
    private String TitleName;
    private Context context;
    private Date mDate = new Date(System.currentTimeMillis());
    private boolean isLoading = false;
    private ArrayList<NewsBean.StoriesBean> mStoriesBeens = new ArrayList<NewsBean.StoriesBean>();
    private String strDate;

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

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close) {

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
                .doOnNext(new Consumer<NewsBean>() {
                    @Override
                    public void accept(NewsBean newsBean) throws Exception {
                        //缓存最新一页的新闻数据
                        CacheUtil.cacheLatestNews(context, newsBean);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsBean newsBean) {

                        loadPage(newsBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                        //如果网络请求失败，就去读取缓存中的数据

                        NewsBean newsBean = CacheUtil.getCacheLatestNews(context);


                        if (newsBean != null) {


                            loadPage(newsBean);

                        }

                    }

                    @Override
                    public void onComplete() {

                        LogUtil.i("完成");

                    }
                });


        /**
         * 分页加载下一页数据
         */
        mHeaderListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {


            }

            @Override
            public void onScroll(final AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


                /**
                 *
                 * 当ListView滑动到最底部进行加载过去的新闻数据
                 *
                 */
                if (firstVisibleItem + visibleItemCount == totalItemCount) {

                    //进行加载前一天的知乎新闻

                    if (isLoading) {

                        isLoading = false;


                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
                        strDate = sf.format(mDate);

                        RetrofitFactory.getZhiHUDailyServiceInstance()
                                .getHistoryNews(strDate)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<NewsBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(NewsBean newsBean) {

                                        newsBean.getStories().get(0).setDate(newsBean.getDate());
                                        mStoriesBeens.addAll(newsBean.getStories());
                                        mAdapter.notifyDataSetChanged();

                                        String dateStr = DateUtil.getWeek(String.valueOf(Integer.valueOf(strDate)-1));


                                        final Snackbar snackbar = Snackbar.make(view, dateStr + " 的内容已备好，请享用", Toast.LENGTH_SHORT);

                                        snackbar.show();

                                        snackbar.setAction("ok", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                snackbar.dismiss();
                                            }
                                        });

                                        snackbar.setActionTextColor(getResources().getColor(R.color.colorGreen));


                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                        Snackbar.make(view, "小猪! 小猪! 网络异常啦", Toast.LENGTH_SHORT)
                                                .setAction("知道啦", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {


                                                    }
                                                })

                                                .show();

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });


                        mDate = DateUtil.getLastDay(mDate);

                    }
                } else {

                    isLoading = true;

                }


            }
        });


        mHeaderListView.setOnTopPagerClickListener(new HeaderListView.TopPagerClickListener() {
            @Override
            public void click(int position, NewsBean.TopStoriesBean storiesBean) {

                LogUtil.i("position:" + position + "新闻id:" + storiesBean.getId());



            }
        });


        /**
         *
         * ListView中item点击监听
         */
        mHeaderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                NewsBean.StoriesBean Story = (NewsBean.StoriesBean) parent.getItemAtPosition(position);

                LinearLayout ll = (LinearLayout)view;

                startActivity(Story.getId(),Story.getTitle());


            }
        });
    }

    /**
     * 开启一个Activity
     * @param id  文章id
     */
    private void startActivity(int id,String title) {

        Intent intent = new Intent(this, ContentActivity.class);
        intent.putExtra("article_id",id);
        intent.putExtra("title",title);


        startActivity(intent);

    }

    /**
     * 加载页面
     *
     * @param newsBean
     */
    private void loadPage(NewsBean newsBean) {


        List<NewsBean.TopStoriesBean> top_stories = newsBean.getTop_stories();
        mHeaderListView.setTopStories(top_stories);

        newsBean.getStories().get(0).setDate(newsBean.getDate());
        mStoriesBeens.addAll(newsBean.getStories());

        mAdapter = new LvShowNewsAdapter(context, mStoriesBeens);

        mHeaderListView.setAdapter(mAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    Date lastData = new Date(System.currentTimeMillis());

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                if (mDrawerLayout.isDrawerOpen(Gravity.START)) {

                    mDrawerLayout.closeDrawer(Gravity.START);
                } else {

                    mDrawerLayout.openDrawer(Gravity.START);
                }


                lastData = DateUtil.getLastDay(lastData);


                break;

        }


        return true;

    }



}
