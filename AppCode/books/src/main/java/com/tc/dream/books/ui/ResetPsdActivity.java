package com.tc.dream.books.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tc.dream.books.R;
import com.tc.dream.books.utils.Util;

import cn.bmob.v3.BmobUser;


/**
 * 找回密码
 * Created by dream on 15/11/21.
 */
public class ResetPsdActivity extends Activity implements View.OnClickListener{

    private EditText etVerifiedEmail;
    private Button btnSendEmail;

    private RelativeLayout rlResetPsdFinished;
    private Button btnBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_psd);
        initView();
    }

    private void initView() {
        etVerifiedEmail = (EditText) findViewById(R.id.et_email);
        btnSendEmail = (Button) findViewById(R.id.btn_send_email);

        rlResetPsdFinished = (RelativeLayout) findViewById(R.id.rl_reset_psd_finished);
        btnBackToLogin = (Button) findViewById(R.id.btn_back_to_login);

        btnSendEmail.setOnClickListener(this);
        btnBackToLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_email:
                String email = etVerifiedEmail.getText().toString();
                if(email.equals(""))
                {
                    toast("请输入邮箱地址");
                }
                else if(!Util.isEmailValid(email)) {
                    toast("请输入有效的邮箱地址");
                }
                else {
                    sendVerifiedEmail(email);
                }
                break;

            case R.id.btn_back_to_login:
                Intent toLoginActivity = new Intent(ResetPsdActivity.this, LoginActivity.class);
                startActivity(toLoginActivity);
                break;
            default:
                break;
        }
    }

    //找回密码：给邮箱发送验证消息
    private void sendVerifiedEmail(final String emailAddress)
    {

    }


    private void toast(String toast)
    {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }
}
