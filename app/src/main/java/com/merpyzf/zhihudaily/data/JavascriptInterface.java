package com.merpyzf.zhihudaily.data;

import android.content.Context;
import android.content.Intent;

import com.merpyzf.zhihudaily.ui.activity.ShowImageActivity;
import com.merpyzf.zhihudaily.util.LogUtil;

/**
 * Created by 春水碧于天 on 2017/4/26.
 */

public class JavascriptInterface {

    private Context context;

    public JavascriptInterface(Context context) {
        this.context = context;
    }

    @android.webkit.JavascriptInterface
    public void openImage(String img) {

        LogUtil.i("网页中的imageUrl"+img);



        Intent intent = new Intent(context,ShowImageActivity.class);

        intent.putExtra("image_url",img);

        context.startActivity(intent);


    }
}
