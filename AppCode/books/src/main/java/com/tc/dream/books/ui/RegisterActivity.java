package com.tc.dream.books.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tc.dream.books.R;
import com.tc.dream.books.model.User;
import com.tc.dream.books.utils.Util;

import cn.bmob.v3.listener.SaveListener;

/**
 * 注册界面
 * Created by dream on 15/11/21.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {

    private Button btnReg;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etComfirmPsd;
    private EditText etPhone;

    private String username = null;
    private String password = null;
    private String comfirmPsd = null;
    private String phone = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        etComfirmPsd = (EditText) findViewById(R.id.et_comfirm_psd);
        etPhone = (EditText) findViewById(R.id.et_phone);

        btnReg = (Button) findViewById(R.id.btn_reg_now);
        btnReg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reg_now:
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                comfirmPsd = etComfirmPsd.getText().toString();
                phone = etPhone.getText().toString();
                if(!Util.isNetworkConnected(this))
                {
                    toast("请检查您的网络");
                }
                else if(username.equals("")||password.equals("")||comfirmPsd.equals("")||phone.equals(""))
                {
                    toast("请您填写正确的信息");
                }
                else if(!Util.isPhoneNumberValid(phone))
                {
                    toast("请输入正确的手机号码");
                }
                else
                {
                    //开始提交注册信息
                    User bu = new User();
                    bu.setUsername(username);
                    bu.setPassword(password);
                    bu.setPhone(phone);
                    bu.signUp(this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            toast("注册成功");
                            Intent backLogin = new Intent(RegisterActivity.this,
                                    LoginActivity.class);
                            startActivity(backLogin);
                            RegisterActivity.this.finish();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            toast("用户名已被注册，请您换个名字");
                        }
                    });
                }
        }
    }

    public void toast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    };
}
