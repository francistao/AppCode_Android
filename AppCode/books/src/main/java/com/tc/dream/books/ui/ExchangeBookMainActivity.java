package com.tc.dream.books.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tc.dream.books.R;
import com.tc.dream.books.adapter.GiveAdapter;
import com.tc.dream.books.adapter.WishAdapter;
import com.tc.dream.books.model.UserGiveBook;
import com.tc.dream.books.model.UserWantBook;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by dream on 15/12/17.
 */
public class ExchangeBookMainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, AdapterView.OnItemClickListener {

    private TextView tvAdd;
    private ListView lvGive;
    private GiveAdapter giveAdapter;

    private SwipeRefreshLayout swipeLayout;

    //下拉刷新
    private static final int STATE_REFRESH = 0;
    private static final int STATE_MORE = 1;

    private int limit = 10;   //每页的数据是10条
    private int curPage = 0;    //当前页的编号，从0开始


    private List<UserGiveBook> bookList = new ArrayList<UserGiveBook>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_book_main);
        initView();
        //获取书籍数据
        getBookItemData();

    }

    private void initView() {
        tvAdd = (TextView) findViewById(R.id.tv_add);
        lvGive = (ListView) findViewById(R.id.lv_give);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.lv_refresh);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        tvAdd.setOnClickListener(this);
        giveAdapter = new GiveAdapter(ExchangeBookMainActivity.this, (ArrayList<UserGiveBook>)bookList);
        lvGive.setAdapter(giveAdapter);
        lvGive.setOnItemClickListener(this);

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

    private void getBookItemData() {
        BmobUser bmobUser = BmobUser.getCurrentUser(this);
        String name = bmobUser.getUsername();
        BmobQuery<UserGiveBook> query = new BmobQuery<UserGiveBook>();
        query.addWhereEqualTo("userName", name);
        query.order("-updatedAt");
        query.findObjects(this, new FindListener<UserGiveBook>() {
            @Override
            public void onSuccess(List<UserGiveBook> list) {
                if (list.size() == 0) {
                    toast("还没有书籍加入心愿哦");
                } else {
                    bookList = list;
                    //通知Adapter更新数据
                    giveAdapter.refresh((ArrayList<UserGiveBook>) bookList);
                }
            }

            @Override
            public void onError(int i, String s) {
                toast("查询失败");
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.tv_add:
                Intent addbookIntent = new Intent(ExchangeBookMainActivity.this, ExchangeBookActivity.class);
                startActivity(addbookIntent);
                break;

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void toast(String toast) {
        Toast.makeText(this, toast,  Toast.LENGTH_SHORT).show();
    };
}
