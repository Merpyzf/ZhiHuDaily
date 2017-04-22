package com.merpyzf.zhihudaily.ui.customui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.merpyzf.zhihudaily.R;

/**
 * Created by 春水碧于天 on 2017/4/22.
 */

public class HeaderListView extends ListView {

    private Context context;

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

    private void InitHeader() {

        View view = View.inflate(context, R.layout.header_listview,null);



    }
}
