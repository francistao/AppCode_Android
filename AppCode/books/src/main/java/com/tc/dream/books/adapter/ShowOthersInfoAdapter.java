package com.tc.dream.books.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tc.dream.books.model.UserWantBook;

import java.util.List;

/**
 * 看到别人的书籍信息适配器
 * Created by dream on 15/12/20.
 */
public class ShowOthersInfoAdapter extends BaseAdapter{

    private Context mContext;

    private List<UserWantBook> mBookList;   //想要的书
    private LayoutInflater mInflater = null;

    public ShowOthersInfoAdapter(Context mContext, List<UserWantBook> mBookList) {
        this.mContext = mContext;
        this.mBookList = mBookList;
    }

    @Override
    public int getCount() {
        return 0;
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
        return null;
    }
}
