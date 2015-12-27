package com.tc.dream.books.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tc.dream.books.R;
import com.tc.dream.books.constant.Constant;
import com.tc.dream.books.model.User;
import com.tc.dream.books.utils.Util;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.update.BmobUpdateAgent;

/**
 * 登录界面
 * Created by dream on 15/11/21.
 */
public class LoginActivity extends Activity implements Constant, View.OnClickListener{

    private static final String TAG = "LoginActivity";

    private Button btnLogin;
    private Button btnReg;
    private Button btnResetPsd;
    private EditText etUsername;
    private EditText etPassword;

    private String username;
    private String password;

    private TextView mUserInfo;
    private ImageView mUserLogo;
    private ImageView mNewLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Bmob自动更新组件
        BmobUpdateAgent.setUpdateOnlyWifi(true);
        BmobUpdateAgent.update(this);

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReg = (Button) findViewById(R.id.btn_register);
        btnResetPsd = (Button) findViewById(R.id.btn_reset_psd);

        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);

        btnLogin.setOnClickListener(this);
        btnReg.setOnClickListener(this);
        btnResetPsd.setOnClickListener(this);

        getUserInfo();
    }

    private void getUserInfo() {
        SharedPreferences sp = getSharedPreferences("UserInfo", 0);
        etUsername.setText(sp.getString("username", null));
        etPassword.setText(sp.getString("password", null));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //登录
            case R.id.btn_login:
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                if(!Util.isNetworkConnected(this))
                {
                    toast("请检查网络状态");
                }
                else if(username.equals("")||password.equals(""))
                {
                    toast("请正确输入账号密码");
                    break;
                }
                else
                {
                    User bu2 = new User();
                    bu2.setUsername(username);
                    bu2.setPassword(password);
                    bu2.login(this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            toast("欢迎来到寻书");
                            //保存用户信息
                            saveUserInfo(username, password);
                            //跳转到主页面
                            Intent toHome = new Intent(LoginActivity.this, BaseActivity.class);
                            startActivity(toHome);
                            finish();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            toast("用户名或密码错误");
                        }
                    });
                }

                break;
            case R.id.btn_reset_psd:
                    Intent toResetPsdActivity = new Intent(LoginActivity.this, ResetPsdActivity.class);
                    startActivity(toResetPsdActivity);
                break;
            case R.id.btn_register:
                    Intent toReg = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(toReg);
                break;

        }
    }
    //保存用户的登录纪录
    private void saveUserInfo(String username, String password)
    {
        SharedPreferences sp = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
    }


    public void toast(String toast)
    {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }
}
