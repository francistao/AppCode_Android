package com.tc.dream.books.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tc.dream.books.R;
import com.tc.dream.books.adapter.holder.RecommendHolder;
import com.tc.dream.books.model.UserGiveBook;

import java.util.List;

/**
 * 推荐书籍适配器
 * Created by dream on 15/12/24.
 */
public class RecommendAdapter extends BaseAdapter{
    private Context mContext;
    private List<UserGiveBook> mBookList;   //所有用户可提供的书
    private LayoutInflater mInflater = null;

    public RecommendAdapter(Context mContext, List<UserGiveBook> mBookList) {
        this.mContext = mContext;
        this.mBookList = mBookList;
        mInflater = LayoutInflater.from(mContext);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecommendHolder recommendHolder;
        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.recommend_book_item, null);
            recommendHolder = new RecommendHolder();
            recommendHolder.userName = (TextView) convertView.findViewById(R.id.tv_user);
            recommendHolder.imgBook = (ImageView) convertView.findViewById(R.id.img_book);
            recommendHolder.tvBookName = (TextView) convertView.findViewById(R.id.tv_book_name);
            recommendHolder.tvWriterName = (TextView) convertView.findViewById(R.id.tv_writer_name);
            recommendHolder.tvPhoneNumber = (TextView) convertView.findViewById(R.id.tv_phone);
            recommendHolder.tvTips = (TextView) convertView.findViewById(R.id.tv_tips);
            convertView.setTag(recommendHolder);
        }
        else
        {
            recommendHolder = (RecommendHolder) convertView.getTag();
        }
        //加载缩略图
        if(mBookList.get(position).getBookPic() != null)
        {
            mBookList.get(position).getBookPic().loadImageThumbnail(mContext, recommendHolder.imgBook, 300, 300, 100);
        }
        recommendHolder.userName.setText("用户：            " + mBookList.get(position).getUserName());
        recommendHolder.tvBookName.setText("书籍名：" + mBookList.get(position).getBookName());
        recommendHolder.tvWriterName.setText("作者：" + mBookList.get(position).getBookWriter());
        recommendHolder.tvPhoneNumber.setText("联系方式：    " + mBookList.get(position).getPhone());
        recommendHolder.tvTips.setText(mBookList.get(position).getPhone());
        return convertView;
    }
    //刷新列表中的数据
    public void refresh(List<UserGiveBook> list)
    {
        mBookList = list;
        notifyDataSetChanged();
    }
}
