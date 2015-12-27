package com.tc.dream.books.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tc.dream.books.R;
import com.tc.dream.books.adapter.MineListAdapter;
import com.tc.dream.books.data.MessageDef;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;

/**
 * 我的
 * Created by dream on 15/12/2.
 */
public class MineActivity extends Activity implements AdapterView.OnItemClickListener {
    private static final String TAG = "MineActivity";
    private String[] userItemNames = {"我的资料"};
    private String[] userItemContents = {""} ;
    private String[] bookItemNames = {"想要的书", "可换的书"};
    private String[] bookItemContents = {"*", ""};
    private String[] aboutItemNames = {"通知中心", "软件相关", "推荐给朋友","退出账号"};
    private String[] aboutItemContents = {"", "", "", ""};
    //个人信息
    private int[] userImgIds = {R.drawable.ic_menu_myplaces};
    //当前想要的书和可以换的书
    private int[] orderImgIds = {R.drawable.ic_menu_find_holo_light, R.drawable.ic_menu_copy_holo_light};
    //通知中心
    private int[] aboutImgIds = {R.drawable.ic_menu_notifications, R.drawable.ic_menu_info_details, R.drawable.ic_menu_share, R.drawable.ic_star_yes};

    private ListView lvMineUser;
    private ListView lvMineBook;
    private ListView lvMineAbout;

    private MineListAdapter userListAdapter;
    private MineListAdapter bookListAdapter;
    private MineListAdapter aboutListAdapter;

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case MessageDef.MINE_FINISH_LOAD_DATA:
                    //Handler收到数据加载完成的消息
                    bookListAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        initView();
    }

    private void initView() {
        lvMineUser = (ListView) findViewById(R.id.lv_mine_user);
        lvMineBook = (ListView) findViewById(R.id.lv_mine_order);
        lvMineAbout = (ListView) findViewById(R.id.lv_mine_about);

        userListAdapter = new MineListAdapter(this, userItemNames, userItemContents, userImgIds);
        bookListAdapter = new MineListAdapter(this, bookItemNames, bookItemContents, orderImgIds);
        aboutListAdapter = new MineListAdapter(this, aboutItemNames, aboutItemContents, aboutImgIds);

        lvMineUser.setAdapter(userListAdapter);
        lvMineBook.setAdapter(bookListAdapter);
        lvMineAbout.setAdapter(aboutListAdapter);

        lvMineUser.setOnItemClickListener(this);
        lvMineBook.setOnItemClickListener(this);
        lvMineAbout.setOnItemClickListener(this);

    }

    //初始化列表菜单中数据
    public void initData(final String type)
    {
        //获取用户
        BmobUser user = BmobUser.getCurrentUser(this);
        userItemNames[0] = user.getUsername();
        //获取数量
//        BmobQuery<>

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //个人资料
        if(parent.getId() == R.id.lv_mine_user)
        {
            switch (position)
            {
                case 0:
                    Intent toMineInfo = new Intent(MineActivity.this, MineInfoActivity.class);
                    startActivity(toMineInfo);
                    break;
                default:
                    break;
            }
        }

        //其他
        if(parent.getId() == R.id.lv_mine_about)
        {
            switch (position)
            {
                case 1:   //软件相关
                    Intent toMineSoft = new Intent(MineActivity.this, MineSoftActivity.class);
                    startActivity(toMineSoft);
                    break;
            }
        }
    }
}
