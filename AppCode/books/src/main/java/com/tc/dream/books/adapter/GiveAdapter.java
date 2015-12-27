package com.tc.dream.books.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tc.dream.books.R;
import com.tc.dream.books.adapter.holder.GivingHolder;
import com.tc.dream.books.adapter.holder.WishingHolder;
import com.tc.dream.books.model.UserGiveBook;
import com.tc.dream.books.model.UserWantBook;

import java.util.List;

/**
 * 用户可以提供的书适配器
 * Created by dream on 15/12/17.
 */
public class GiveAdapter extends BaseAdapter{

    private Context mContext;

    private List<UserGiveBook> mBookList;   //可提供的书
    private LayoutInflater mInflater = null;

    public GiveAdapter(Context context, List<UserGiveBook> mBookList)
    {
        this.mBookList = mBookList;
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return mBookList.size();
    }

    @Override
    public Object getItem(int position) {
        return mBookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //刷新列表中的数据
    public void refresh(List<UserGiveBook> list)
    {
        mBookList = list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GivingHolder givingHolder;
        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.giving_book_item, null);
            givingHolder = new GivingHolder();
            givingHolder.imgBook = (ImageView) convertView.findViewById(R.id.img_book);
            givingHolder.tvBookName = (TextView) convertView.findViewById(R.id.tv_book_name);
            givingHolder.tvWriterName = (TextView) convertView.findViewById(R.id.tv_writer_name);
            givingHolder.tvPhoneNumber = (TextView) convertView.findViewById(R.id.tv_phone);
            givingHolder.tvTips = (TextView) convertView.findViewById(R.id.tv_tips);
            convertView.setTag(givingHolder);
        }
        else
        {
            givingHolder = (GivingHolder) convertView.getTag();
        }
        //加载缩略图
        if(mBookList.get(position).getBookPic() != null)
        {
            mBookList.get(position).getBookPic().loadImageThumbnail(mContext, givingHolder.imgBook, 300, 300, 100);
        }
        givingHolder.tvBookName.setText("书籍名：        " + mBookList.get(position).getBookName());
        givingHolder.tvWriterName.setText("作者：            " + mBookList.get(position).getBookWriter());
        givingHolder.tvPhoneNumber.setText("联系方式：    " + mBookList.get(position).getPhone());
        givingHolder.tvTips.setText(mBookList.get(position).getPhone());
        return convertView;
    }
}
