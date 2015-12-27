package com.tc.dream.books.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tc.dream.books.R;
import com.tc.dream.books.model.FeedBack;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * 意见反馈界面
 * Created by dream on 15/12/11.
 */
public class FeedBackActivity extends Activity implements View.OnClickListener {

    private EditText etContent;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();
    }

    private void initView() {
        etContent = (EditText) findViewById(R.id.et_feedback_content);
        btnSubmit = (Button) findViewById(R.id.btn_feedback_submit);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_feedback_submit:
                submit();
                break;
            default:
                break;
        }
    }

    private void submit() {
        String content = etContent.getText().toString();
        if(content.equals(""))
        {
            toast("请先输入意见哦");
        }
        else
        {
            BmobUser user = BmobUser.getCurrentUser(this);
            FeedBack fb = new FeedBack();
            fb.getUsername(user.getUsername());
            fb.setEmail(user.getEmail());
            fb.setContent(content);
            fb.save(this, new SaveListener() {
                @Override
                public void onSuccess() {
                    toast("提交成功");
                    back();
                }

                @Override
                public void onFailure(int i, String s) {
                    toast("提交失败");
                }
            });
        }
    }

    private void back() {
        finish();
    }

    public void clickFeedBack(View v) {
        finish();
    }

    private void toast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }
}
