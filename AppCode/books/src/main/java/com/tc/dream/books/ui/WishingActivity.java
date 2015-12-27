package com.tc.dream.books.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tc.dream.books.R;
import com.tc.dream.books.adapter.WishAdapter;
import com.tc.dream.books.model.UserWantBook;
import com.tc.dream.books.view.SilderListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

/**
 * 心愿界面
 * Created by dream on 15/12/11.
 */
public class WishingActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String ET_BOOK_NAME = "et_book_name";
    private static final String ET_WRITER_NAME = "et_writer_name";
    private static final String ET_PHONENUMBER = "et_phonenumber";
    private static final String ET_TIPS = "et_tips";
    private static final String WISHINGACTIVITY = "WishingActivity";

    private TextView tv_add;
    public SilderListView lv_wish;
    private WishAdapter wishAdapter;

    private SwipeRefreshLayout swipeLayout;
    //下拉刷新
    private static final int STATE_REFRESH = 0;
    private static final int STATE_MORE = 1;

    private int limit = 10;   //每页的数据是10条
    private int curPage = 0;    //当前页的编号，从0开始

    private List<UserWantBook> bookList = new ArrayList<UserWantBook>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishing);

        //获取商店数据
        initView();
        //获取书籍数据
        getBookItemData();
    }

    private void getBookItemData() {
        BmobUser bmobUser = BmobUser.getCurrentUser(this);
        String name = bmobUser.getUsername();
        BmobQuery<UserWantBook> query = new BmobQuery<UserWantBook>();
        query.addWhereEqualTo("userName", name);
        query.order("-updatedAt");

        query.findObjects(this, new FindListener<UserWantBook>() {
            @Override
            public void onSuccess(List<UserWantBook> list) {
                if (list.size() == 0) {
                    toast("还没有书籍加入心愿哦");
                } else {
                    bookList = list;
                    //通知Adapter更新数据
                    wishAdapter.refresh((ArrayList<UserWantBook>) bookList);
                }
            }

            @Override
            public void onError(int i, String s) {
                toast("查询失败");
            }
        });
    }

    private void initView() {
        tv_add = (TextView) findViewById(R.id.tv_add);
        lv_wish = (SilderListView) findViewById(R.id.lv_wish);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.lv_refresh);

        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        tv_add.setOnClickListener(this);
        wishAdapter = new WishAdapter(this, (ArrayList<UserWantBook>) bookList);
        lv_wish.setAdapter(wishAdapter);
        lv_wish.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add:
                Intent addbookIntent = new Intent(WishingActivity.this, AddWantBookActivity.class);
                startActivityForResult(addbookIntent, 1);
                break;

        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode)
//        {
//            case 1:
//                String bookName = data.getStringExtra(ET_BOOK_NAME);
//                String writerName = data.getStringExtra(ET_WRITER_NAME);
//                String phoneNumber = data.getStringExtra(ET_PHONENUMBER);
//                String tips = data.getStringExtra(ET_TIPS);
////                Log.d(WISHINGACTIVITY, bookName+"+"+writerName+"+"+phoneNumber+"+"+tips);
//            break;
//        }
//    }


    private void toast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    ;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * 分页获取数据
     * 页码
     * ListView的操作类型（下拉刷新，上拉加载更多）
     */
    private void queryData(final int page, final int actionType) {
        BmobQuery<UserWantBook> query = new BmobQuery<UserWantBook>();
        UserWantBook userWantBook = new UserWantBook();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
                //获取书籍数据
                getBookItemData();
            }
        }, 1000);
    }
}
