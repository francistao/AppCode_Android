package com.tc.dream.books.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tc.dream.books.R;
import com.tc.dream.books.adapter.holder.MineListHolder;

/**
 * 我的
 * 个人资料
 * Created by dream on 15/12/6.
 */
public class MineListAdapter extends BaseAdapter{

    private Context mContext;
    private String[] mItemNames;     //项目列表名称
    private String[] mItemContents;     //项目列表的备注值
    private int[] mItemImgIds;      //项目列表Icon
    private LayoutInflater mInflater = null;

    public MineListAdapter(Context context, String[] names, String[] contents, int[] imgIds) {
        this.mContext = context;
        this.mItemNames = names;
        this.mItemContents = contents;
        this.mItemImgIds = imgIds;
        mInflater = LayoutInflater.from(context);
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
            convertView = mInflater.inflate(R.layout.mine_list_item, null);
            holder = new MineListHolder();
            holder.imgItem = (ImageView) convertView.findViewById(R.id.img_item);
            holder.tvItemName = (TextView) convertView.findViewById(R.id.tv_item_name);
            holder.tvItemContent = (TextView) convertView.findViewById(R.id.tv_item_content);
            convertView.setTag(holder);
        }
        else
        {
            holder = (MineListHolder) convertView.getTag();
        }
        holder.imgItem.setBackgroundResource(mItemImgIds[position]);
        holder.tvItemName.setText(mItemNames[position]);
        holder.tvItemContent.setText(mItemContents[position]);
        return convertView;
    }
}
