package com.merpyzf.zhihudaily.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.merpyzf.zhihudaily.R;
import com.merpyzf.zhihudaily.data.entity.NewsBean;
import com.merpyzf.zhihudaily.util.DateUtil;
import com.merpyzf.zhihudaily.util.LogUtil;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 春水碧于天 on 2017/4/23.
 */

public class LvShowNewsAdapter extends BaseAdapter {

    private Context context;
    private NewsBean newsBean;

    public LvShowNewsAdapter(Context context,NewsBean newsBean) {

        this.context = context;
        this.newsBean = newsBean;

    }

    @Override
    public int getCount() {
        return newsBean.getStories().size();
    }

    @Override
    public Object getItem(int position) {

        return position;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if(convertView == null){

                convertView = View.inflate(context,R.layout.item_listview,null);
                viewHolder = new ViewHolder(convertView);

                convertView.setTag(viewHolder);

        }else {

            viewHolder = (ViewHolder) convertView.getTag();

        }


        NewsBean.StoriesBean storiesBean = newsBean.getStories().get(position);



            Glide.with(context)
                    .load(storiesBean.getImages().get(0))
                    .centerCrop()
                    .into(viewHolder.iv_news_show);

            viewHolder.tv_title.setText(storiesBean.getTitle());






                //表示时间为今日
                if (newsBean.getStories().get(position).getDate()!=null&&newsBean.getStories().get(position).getDate().equals(DateUtil.getNowDay(new Date(System.currentTimeMillis())))) {

                    viewHolder.tv_show_date.setVisibility(View.VISIBLE);
                    viewHolder.tv_show_date.setText("今日热闻");




                } else if (newsBean.getStories().get(position).getDate()!=null&& !newsBean.getStories().get(position).getDate().equals(DateUtil.getNowDay(new Date(System.currentTimeMillis())))){

                    //表示是过去的时间
                    LogUtil.i("过去的时间");

                    viewHolder.tv_show_date.setVisibility(View.VISIBLE);
                    viewHolder.tv_show_date.setText(newsBean.getDate());


                }else {

                    viewHolder.tv_show_date.setVisibility(View.GONE);

                    viewHolder.tv_show_date.setText("");
                }


        return convertView;
    }

    class ViewHolder {

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }

        @BindView(R.id.tv_title) TextView tv_title;
        @BindView(R.id.iv_news_show) ImageView iv_news_show;
        @BindView(R.id.tv_show_date) TextView tv_show_date;

    }



}
