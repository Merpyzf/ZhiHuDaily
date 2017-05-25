package com.merpyzf.zhihudaily.data;

import android.content.Context;
import android.content.Intent;

import com.merpyzf.zhihudaily.ui.activity.ShowPictureActivity;
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
    public void openImage(String allImg,String img) {

        LogUtil.i("网页中的imageUrl"+img);
        LogUtil.i("网页中的所有imageUrl"+allImg);



        Intent intent = new Intent(context,ShowPictureActivity.class);

        intent.putExtra("image_url",img);
        intent.putExtra("page_image",allImg);

        context.startActivity(intent);


    }
}
