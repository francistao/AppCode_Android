package com.tc.dream.books.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tc.dream.books.R;
import com.tc.dream.books.adapter.holder.WishingHolder;
import com.tc.dream.books.model.UserGiveBook;
import com.tc.dream.books.model.UserWantBook;
import com.tc.dream.books.ui.ExchangeBookMainActivity;
import com.tc.dream.books.ui.WishingActivity;
import com.tc.dream.books.view.SliderView;

import java.util.ArrayList;
import java.util.List;

/**
 * 心愿适配器
 * Created by dream on 15/12/13.
 */
public class WishAdapter extends BaseAdapter{

    private Context mContext;

    private List<UserWantBook> mBookList;   //想要的书
    private LayoutInflater mInflater = null;

    public WishAdapter(Context context, ArrayList<UserWantBook> mBookList)
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
    public void refresh(List<UserWantBook> list)
    {
        mBookList = list;
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WishingHolder wishingHolder;
        SliderView slideView = (SliderView) convertView;
        if(slideView == null)
        {
            View itemView = mInflater.inflate(R.layout.wishing_book_item, null);
            slideView = new SliderView(mContext);
            slideView.setContentView(itemView);

            wishingHolder = new WishingHolder();
            wishingHolder.imgBook = (ImageView) slideView.findViewById(R.id.img_book);
            wishingHolder.tvBookName = (TextView) slideView.findViewById(R.id.tv_book_name);
            wishingHolder.tvWriterName = (TextView) slideView.findViewById(R.id.tv_writer_name);
            wishingHolder.tvPhoneNumber = (TextView) slideView.findViewById(R.id.tv_phone);
            wishingHolder.tvTips = (TextView) slideView.findViewById(R.id.tv_tips);
            wishingHolder.deleteHolder = (ViewGroup) slideView.findViewById(R.id.holder);
            slideView.setTag(wishingHolder);
        }
        else
        {
            wishingHolder = (WishingHolder) slideView.getTag();
        }

        slideView.shrink();
        //加载缩略图
        if(mBookList.get(position).getPicBook() != null)
        {
            mBookList.get(position).getPicBook().loadImageThumbnail(mContext, wishingHolder.imgBook, 300, 300, 100);
        }
        wishingHolder.tvBookName.setText("书籍名：        " + mBookList.get(position).getBookName());
        wishingHolder.tvWriterName.setText("作者：            " + mBookList.get(position).getBookWriter());
        wishingHolder.tvPhoneNumber.setText("联系方式：    " + mBookList.get(position).getPhone());
        wishingHolder.tvTips.setText(mBookList.get(position).getPhone());
        wishingHolder.deleteHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.holder) {

                }
            }
        });
        return slideView;
    }
}
