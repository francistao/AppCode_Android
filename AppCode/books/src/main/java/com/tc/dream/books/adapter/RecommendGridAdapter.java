package com.tc.dream.books.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tc.dream.books.R;
import com.tc.dream.books.adapter.holder.RecommendHolder;

/**
 * 推荐首页gridview适配器
 * Created by dream on 15/12/27.
 */
public class RecommendGridAdapter extends BaseAdapter{

    private Context context;
    private String[] titles;
    public RecommendGridAdapter(Context context, String[] titles)
    {
        this.context = context;
        this.titles = titles;
    }
    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.recommend_grid_item, null);
        ImageView image = (ImageView) view.findViewById(R.id.iv_image);
        TextView title =  (TextView) view.findViewById(R.id.tv_title);
        title.setText(titles[position]);

        return view;
    }
}
