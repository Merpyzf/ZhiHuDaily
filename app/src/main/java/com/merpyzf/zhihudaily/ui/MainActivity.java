package com.merpyzf.zhihudaily.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.FrameLayout;
import android.widget.ListView;

import com.merpyzf.zhihudaily.R;
import com.merpyzf.zhihudaily.data.entity.ThemeBean;
import com.merpyzf.zhihudaily.ui.adapter.LvThemeAdapter;
import com.merpyzf.zhihudaily.ui.fragment.MainFragment;
import com.merpyzf.zhihudaily.util.DateUtil;
import com.merpyzf.zhihudaily.util.http.RetrofitFactory;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.dl_left)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.fl_content)
    FrameLayout fl_content;
    @BindView(R.id.lv_theme)
    ListView lv_theme;


    private Context context;
    private ActionBarDrawerToggle mDrawerToggle;
    private FragmentManager manager;
    private FragmentTransaction mTransaction;


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

        manager = getFragmentManager();
        mTransaction = manager.beginTransaction();
        mTransaction.replace(R.id.fl_content, new MainFragment());
        mTransaction.commit();

        // TODO: 2017/5/23 社会化分享功能

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


        LoadDrawerLayout();


    }

    /**
     * 加载抽屉菜单主题日报的数据
     */
    private void LoadDrawerLayout() {


        RetrofitFactory.getZhiHUDailyServiceInstance()
                .getThemes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ThemeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ThemeBean value) {


                        LvThemeAdapter lvThemeAdapter = new LvThemeAdapter(context, value.getOthers());

                        lv_theme.setAdapter(lvThemeAdapter);


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

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


    @Override
    public void onBackPressed() {


        moveTaskToBack(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
