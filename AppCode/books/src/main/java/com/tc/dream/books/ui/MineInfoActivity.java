package com.tc.dream.books.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.tc.dream.books.R;
import com.tc.dream.books.data.MessageDef;
import com.tc.dream.books.model.User;

import cn.bmob.v3.BmobUser;

/**
 * 个人信息界面
 * Created by dream on 15/12/10.
 */
public class MineInfoActivity extends Activity {


    private TextView tvUsername;
    private TextView tvSchool;
    private TextView tvCademy;
    private TextView tvPhone;
    private TextView tvQQ;

    private User curUser;
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case MessageDef.MINE_INFO_FINISH_FIND_USER:
                    initView();
                    break;
                default:
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_info);
        getCurUser();
    }

    private void initView()
    {
        tvUsername = (TextView) findViewById(R.id.tv_mineinfo_username);
        tvSchool = (TextView) findViewById(R.id.tv_mineinfo_school);
        tvCademy = (TextView) findViewById(R.id.tv_mineinfo_cademy);
        tvPhone = (TextView) findViewById(R.id.tv_mineinfo_phone);
        tvQQ = (TextView) findViewById(R.id.tv_mineinfo_qq);

        tvUsername.setText(curUser.getUsername());
        tvSchool.setText(curUser.getSchool());
        tvCademy.setText(curUser.getCademy());
        tvPhone.setText(curUser.getPhone());
        tvQQ.setText(curUser.getQq());
    }

    private void getCurUser() {
        curUser = BmobUser.getCurrentUser(this, User.class);
        if(curUser!= null)
        {
            Message msg = new Message();
            msg.what = MessageDef.MINE_INFO_FINISH_FIND_USER;
            mHandler.sendMessage(msg);
        }
    }

    private void refresh()
    {
        getCurUser();
        initView();
    }

    public void clickEdit(View v)
    {
        Intent toEditMineInfo = new Intent(MineInfoActivity.this, MineInfoEditActivity.class);
        startActivityForResult(toEditMineInfo, 200);
    }
    public void clickBack(View v)
    {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 200)
        {
            refresh();
        }
    }
}
