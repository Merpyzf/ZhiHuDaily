package com.merpyzf.zhihudaily.util;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.merpyzf.zhihudaily.R;
import com.merpyzf.zhihudaily.data.ZhiHuDailyApplication;

/**
 * Created by 春水碧于天 on 2017/4/26.
 */

public class SnackBarUtil {


    public  static void SnackTip(View view,String tip) {



        final Snackbar snackBar = Snackbar.make(view,tip, Toast.LENGTH_SHORT);
        snackBar.show();

        snackBar.setAction("ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBar.dismiss();
            }
        });

        snackBar.setActionTextColor(ZhiHuDailyApplication.getContext().getResources().getColor(R.color.colorGreen));


    }

}
