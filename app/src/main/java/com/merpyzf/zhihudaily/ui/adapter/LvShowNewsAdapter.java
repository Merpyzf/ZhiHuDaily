package com.merpyzf.zhihudaily.ui.adapter;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.merpyzf.zhihudaily.R;
import com.merpyzf.zhihudaily.data.entity.NewsBean;
import com.merpyzf.zhihudaily.util.DateUtil;
import com.merpyzf.zhihudaily.util.LogUtil;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 春水碧于天 on 2017/4/23.
 */

public class LvShowNewsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NewsBean.StoriesBean> StoriesBean;
    private ActionBar actionBar;


    public LvShowNewsAdapter(Context context, ArrayList<NewsBean.StoriesBean> StoriesBean) {

        this.context = context;
        this.StoriesBean = StoriesBean;
    }

    @Override
    public int getCount() {
        return StoriesBean.size();
    }

    @Override
    public Object getItem(int position) {

        return StoriesBean.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {

            convertView = View.inflate(context, R.layout.item_listview, null);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }


        NewsBean.StoriesBean storiesBean = StoriesBean.get(position);


        Glide.with(context)
                .load(storiesBean.getImages().get(0))
                .priority(Priority.HIGH)
                .centerCrop()
                .into(viewHolder.iv_news_show);

        viewHolder.tv_title.setText(storiesBean.getTitle());

        //新闻时间
        String newsDate = StoriesBean.get(position).getDate();
        //当前系统的时间
        String sysDate = DateUtil.getNowDay(new Date(System.currentTimeMillis()));


        LogUtil.i("时间==》"+newsDate);



        //表示时间为今日
        if (StoriesBean.get(position).getDate() != null) {

            if(newsDate.equals(sysDate)) {


                viewHolder.tv_show_date.setVisibility(View.VISIBLE);
                viewHolder.tv_show_date.setText("今日热闻");

            }else {

                String formatDate = DateUtil.getWeek(newsDate);

                viewHolder.tv_show_date.setVisibility(View.VISIBLE);
                viewHolder.tv_show_date.setText(formatDate);

            }


        }  else {

            viewHolder.tv_show_date.setVisibility(View.GONE);

            viewHolder.tv_show_date.setText("");
        }


        return convertView;
    }

    class ViewHolder {

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.iv_news_show)
        ImageView iv_news_show;
        @BindView(R.id.tv_show_date)
        TextView tv_show_date;

    }


}
