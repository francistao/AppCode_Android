package com.tc.dream.books.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tc.dream.books.R;
import com.tc.dream.books.model.User;
import com.tc.dream.books.model.UserWantBook;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by dream on 15/12/11.
 */
public class AddWantBookActivity extends Activity implements View.OnClickListener {


    private static final String ET_BOOK_NAME = "et_book_name";
    private static final String ET_WRITER_NAME = "et_writer_name";
    private static final String ET_PHONENUMBER = "et_phonenumber";
    private static final String ET_TIPS = "et_tips";

    private Button btnAdd;
    private EditText et_book_name;
    private EditText et_writer_name;
    private EditText et_phonenumber;
    private EditText et_tips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_want_book);
        initView();
    }

    private void initView() {
        btnAdd = (Button) findViewById(R.id.btn_add);
        et_book_name = (EditText) findViewById(R.id.et_book_name);
        et_writer_name = (EditText) findViewById(R.id.et_writer_name);
        et_phonenumber = (EditText) findViewById(R.id.et_phone_name);
        et_tips = (EditText) findViewById(R.id.et_tips_name);
        btnAdd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_add:
                saveUserWant();
//                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra(ET_BOOK_NAME, et_book_name.getText().toString());
                intent.putExtra(ET_WRITER_NAME, et_writer_name.getText().toString());
                intent.putExtra(ET_PHONENUMBER, et_phonenumber.getText().toString());
                intent.putExtra(ET_TIPS, et_tips.getText().toString());
                this.setResult(1, intent);
                finish();
                break;
        }

    }

    private void saveUserWant() {
        UserWantBook book = new UserWantBook();
        BmobUser user = BmobUser.getCurrentUser(this);
        book.setUserName(user.getUsername());
        book.setBookName(et_book_name.getText().toString());
        book.setBookWriter(et_writer_name.getText().toString());
        book.setPhone(et_phonenumber.getText().toString());
        book.setTips(et_tips.getText().toString());
        book.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
//                Toast.makeText(AddWantBookActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(AddWantBookActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
            }
        });




    }


}
