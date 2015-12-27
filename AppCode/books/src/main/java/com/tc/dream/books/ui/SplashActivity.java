package com.tc.dream.books.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.tc.dream.books.constant.Constant;
import com.tc.dream.books.R;

import cn.bmob.v3.Bmob;

/**
 * 闪屏页面
 * Created by dream on 15/11/21.
 */
public class SplashActivity extends Activity implements Constant{


    private static final int GO_HOME = 100;
    private static final int GO_LOGIN = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化BmobSDK
        Bmob.initialize(this, APPID);
        setContentView(R.layout.activity_splash);

        mHandler.sendEmptyMessageDelayed(GO_LOGIN, 3000);
    }

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case GO_HOME:
                    break;
                case GO_LOGIN:
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };
}
