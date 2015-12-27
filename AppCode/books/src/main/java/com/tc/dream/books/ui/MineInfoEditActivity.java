package com.tc.dream.books.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tc.dream.books.R;
import com.tc.dream.books.data.MessageDef;
import com.tc.dream.books.model.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 修改个人资料
 * Created by dream on 15/12/10.
 */
public class MineInfoEditActivity extends Activity{

    private EditText etUsername;
    private EditText etSchool;
    private EditText etCademy;
    private EditText etPhone;
    private EditText etQQ;

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

    private void initView() {
        etUsername = (EditText) findViewById(R.id.et_mineinfo_username);
        etSchool = (EditText) findViewById(R.id.et_mineinfo_school);
        etCademy = (EditText) findViewById(R.id.et_mineinfo_cademy);
        etPhone = (EditText) findViewById(R.id.et_mineinfo_phone);
        etQQ = (EditText) findViewById(R.id.et_mineinfo_qq);

        etUsername.setText(curUser.getUsername());
        etSchool.setText(curUser.getSchool());
        etCademy.setText(curUser.getCademy());
        etPhone.setText(curUser.getPhone());
        etQQ.setText(curUser.getQq());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_info_edit);
        setCurUser();

    }

    private void setCurUser() {
        BmobUser bmobUser = BmobUser.getCurrentUser(this);
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("objectId", bmobUser.getObjectId());
        query.findObjects(this, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                curUser = list.get(0);
                Message msg = new Message();
                msg.what = MessageDef.MINE_INFO_FINISH_FIND_USER;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(int i, String s) {
                toast("获取当前用户失败");
            }
        });
    }


    public void clickSave(View v) {
        saveUserInfo();
    }

    private void saveUserInfo() {
        if(curUser == null)
        {
            toast("请先登录");
            Intent toLogin = new Intent(MineInfoEditActivity.this, LoginActivity.class);
            startActivity(toLogin);
        }
        else
        {
            curUser.setUsername(etUsername.getText().toString());
            curUser.setSchool(etSchool.getText().toString());
            curUser.setCademy(etCademy.getText().toString());
            curUser.setPhone(etPhone.getText().toString());
            curUser.setQq(etQQ.getText().toString());
            curUser.update(this, curUser.getObjectId(), new UpdateListener() {
                @Override
                public void onSuccess() {
                    Intent back = new Intent(MineInfoEditActivity.this, MineInfoActivity.class);
                    setResult(200, back);
                    finish();
                    toast("个人资料修改成功");
                }

                @Override
                public void onFailure(int i, String s) {
                    toast("更新失败");
                }
            });
        }
    }

    public void clickCancel(View v) {
        finish();
    }

    private void toast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }
}
