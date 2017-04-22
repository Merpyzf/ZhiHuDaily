package com.merpyzf.zhihudaily.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 春水碧于天 on 2017/4/22.
 */

public class ToastUtil {

    public static void show(Context context,String content){

        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }
}
