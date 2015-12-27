package com.tc.dream.books.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tc.dream.books.R;

/**
 * 发现界面
 * Created by dream on 15/12/11.
 */
public class FoundingActivity extends Activity implements View.OnClickListener {

    private RelativeLayout rl_can_exchange_book;
    private RelativeLayout rl_food_good_book;
    private RelativeLayout rl_donate_book;
    private RelativeLayout rl_book_note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_founding);
        initView();
    }

    private void initView() {
        rl_can_exchange_book = (RelativeLayout) findViewById(R.id.rl_can_exchange_book);
        rl_food_good_book = (RelativeLayout) findViewById(R.id.rl_food_good_book);
        rl_donate_book = (RelativeLayout) findViewById(R.id.rl_donate_book);
        rl_book_note = (RelativeLayout) findViewById(R.id.rl_book_note);
        rl_can_exchange_book.setOnClickListener(this);
        rl_food_good_book.setOnClickListener(this);
        rl_donate_book.setOnClickListener(this);
        rl_book_note.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.rl_book_note:
                startActivity(new Intent(FoundingActivity.this, BookNoteActivity.class));
                break;
            case R.id.rl_can_exchange_book:
                startActivity(new Intent(FoundingActivity.this, ExchangeBookMainActivity.class));
                break;
            case R.id.rl_donate_book:
                startActivity(new Intent(FoundingActivity.this, DonateBookActivity.class));
                break;
            default:
                break;
        }
    }
}
