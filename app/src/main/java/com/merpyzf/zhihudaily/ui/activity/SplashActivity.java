package com.merpyzf.zhihudaily.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.merpyzf.zhihudaily.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView iv_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        InitUI();

    }

    private void InitUI() {

        iv_splash = (ImageView) findViewById(R.id.iv_splash);
    }
}
