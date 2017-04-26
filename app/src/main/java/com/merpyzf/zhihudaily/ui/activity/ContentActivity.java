package com.merpyzf.zhihudaily.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.merpyzf.zhihudaily.R;
import com.merpyzf.zhihudaily.data.JavascriptInterface;
import com.merpyzf.zhihudaily.data.entity.CollectBean;
import com.merpyzf.zhihudaily.data.entity.ContentBean;
import com.merpyzf.zhihudaily.util.DBUtil;
import com.merpyzf.zhihudaily.util.LogUtil;
import com.merpyzf.zhihudaily.util.SnackBarUtil;
import com.merpyzf.zhihudaily.util.http.RetrofitFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class ContentActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private Context context;


    @BindView(R.id.iv_bg)
    ImageView iv_bg;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsing_toolbar;
    @BindView(R.id.tv_image_source)
    TextView tv_image_source;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.CoordinatorLayout)
    CoordinatorLayout CoordinatorLayout;


    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        actionBar = getSupportActionBar();


        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        mWebView.getSettings().setDatabaseEnabled(true);
        // 开启Application Cache功能
        mWebView.getSettings().setAppCacheEnabled(true);

        mWebView.addJavascriptInterface(new JavascriptInterface(this), "imagelistner");


        int article_id = getIntent().getIntExtra("article_id", -1);
        String article_title = getIntent().getStringExtra("title");

        actionBar.setTitle(article_title);




        RetrofitFactory.getZhiHUDailyServiceInstance()
                .getNewsContent(String.valueOf(article_id))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ContentBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(final ContentBean value) {

                        Glide.with(context)
                                .load(value.getImage())
                                .centerCrop()
                                .into(iv_bg);

                        loadHtmlResource(value);

                        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                String nowDate = format.format(new Date(System.currentTimeMillis()));

                                CollectBean collectBean = new CollectBean(value.getId(), value.getTitle(), value.getImage(), nowDate);

                                int insertResult = DBUtil.insert(collectBean);

                                LogUtil.i("文章Id:" + value.getId() + "  文章标题:" + value.getTitle() + "  收藏结果：" + insertResult);


                                if (insertResult == -1) {

                                    SnackBarUtil.SnackTip(CoordinatorLayout, "此篇文章您已经收藏啦😘");

                                } else {


                                    SnackBarUtil.SnackTip(CoordinatorLayout, "文章收藏成功😄");

                                }


                                LogUtil.i("fab的点击事件执行了！");


                            }
                        });


                        /**
                         *
                         * 给Fab按钮设置背景颜色，貌似无用的功能
                         *
                         */
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                try {

                                    final Bitmap bmp = Glide.with(context)
                                            .load(value.getImage())
                                            .asBitmap()
                                            .into(FutureTarget.SIZE_ORIGINAL, FutureTarget.SIZE_ORIGINAL)
                                            .get();


                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            Palette palette = Palette.from(bmp).generate();

                                            int mutedColor = palette.getMutedColor(getResources().getColor(R.color.colorGreen));

                                            int vibrantColor = palette.getDarkVibrantColor(getResources().getColor(R.color.colorGreen));

                                            fab.setBackgroundTintList(ColorStateList.valueOf(mutedColor));

                                            fab.setRippleColor(vibrantColor);

                                        }
                                    });

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }


                            }
                        }).start();

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        /**
         * 监听webView加载页面是否完成
         */
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

//                http://news-at.zhihu.com/api/4/story-extra/#{id}

                LogUtil.i("url==>" + url + "加载结束");
                addImageClickListner();



            }


            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);

                LogUtil.i("加载的图片资源: "+url);
            }


            //当加载到404的时候调用该方法
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });


    }


    // 注入js函数监听
    private void addImageClickListner () {
        // 这段js函数的功能，遍历所有的img节，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        mWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }



    /**
     * webView加载html资源
     */
    private void loadHtmlResource(ContentBean contentBean) {

        LogUtil.i("新闻id" + contentBean.getId());
        LogUtil.i("新闻标题:" + contentBean.getTitle());
        LogUtil.i("新闻图片:" + contentBean.getImage());


        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/news.css\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + contentBean.getBody() + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
        mWebView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);

        tv_image_source.setText(contentBean.getImage_source());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_content_activity, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case android.R.id.home:

                onBackPressed();

                break;


            case R.id.item_comment:

                // TODO: 2017/4/25  查看评论的页面


                break;


        }


        return true;
    }

    @Override
    protected void onDestroy() {


        if(mWebView != null){

            mWebView.clearHistory();

            ((ViewGroup)mWebView.getParent()).removeView(mWebView);

            mWebView = null;

        }

        super.onDestroy();



    }
}
