package com.merpyzf.zhihudaily.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.merpyzf.zhihudaily.R;
import com.merpyzf.zhihudaily.data.entity.ThemeBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 春水碧于天 on 2017/4/25.
 */

public class LvThemeAdapter extends BaseAdapter {

    private List<ThemeBean.OthersBean> themes;
    private Context context;

    public LvThemeAdapter(Context context, List<ThemeBean.OthersBean> themes) {

        this.context = context;
        this.themes = themes;
    }

    @Override
    public int getCount() {
        return themes.size();
    }

    @Override
    public Object getItem(int position) {
        return themes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if(convertView ==null){


            convertView = View.inflate(context,R.layout.item_listview_theme,null);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);


        }else {

            viewHolder = (ViewHolder) convertView.getTag();

        }


        viewHolder.tv_theme_name.setText(themes.get(position).getName());

        return convertView;
    }


    class ViewHolder {


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @BindView(R.id.tv_theme_name)
        TextView tv_theme_name;
    }

}
