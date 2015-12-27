package com.tc.dream.books.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tc.dream.books.R;
import com.tc.dream.books.adapter.holder.MineListHolder;

/**
 * 项目列表适配器
 * Created by dream on 15/12/10.
 */
public class MineSoftAdapter extends BaseAdapter{


    private Context mContext;

    private String[] mItemNames;   //项目列表名称
    private String[] mItemContents;    //列表项目的备注值
    private LayoutInflater mInflater = null;

    public MineSoftAdapter(Context context, String[] mItemNames, String[] mItemContents) {
        mContext = context;
        this.mItemNames = mItemNames;
        this.mItemContents = mItemContents;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mItemNames.length;
    }

    @Override
    public Object getItem(int position) {
        return mItemNames[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MineListHolder holder;
        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.mine_soft_list_item, null);
            holder = new MineListHolder();
            holder.tvItemName = (TextView) convertView.findViewById(R.id.tv_item_name);
            holder.tvItemContent = (TextView) convertView.findViewById(R.id.tv_item_content);
            convertView.setTag(holder);
        }
        else
        {
            holder = (MineListHolder) convertView.getTag();
        }
        holder.tvItemName.setText(mItemNames[position]);
        holder.tvItemContent.setText(mItemContents[position]);
        return convertView;
    }
}
