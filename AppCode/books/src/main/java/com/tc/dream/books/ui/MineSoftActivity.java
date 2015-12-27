package com.tc.dream.books.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.tc.dream.books.R;
import com.tc.dream.books.adapter.MineSoftAdapter;

/**
 * 软件相关
 * Created by dream on 15/12/10.
 */
public class MineSoftActivity extends Activity implements AdapterView.OnItemClickListener {

    private String[] softItemNames = {"意见反映", "检查更新", "使用协议", "关于我们"};
    private String[] softItemContents = {"", "", "", ""};
    private ListView lvMineSoft;


    private MineSoftAdapter softListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft);
        initView();
    }

    private void initView() {
        lvMineSoft = (ListView) findViewById(R.id.lv_mine_soft);
        softListAdapter = new MineSoftAdapter(this, softItemNames, softItemContents);
        lvMineSoft.setAdapter(softListAdapter);
        lvMineSoft.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position)
        {
            case 0:
                Intent toFeedBack = new Intent(MineSoftActivity.this, FeedBackActivity.class);
                startActivity(toFeedBack);
                break;
            case 1:
                toast("已经是最新版本");
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
    }

    public void clickSoftBack(View v) {
        finish();
    }

    private void toast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }
}
