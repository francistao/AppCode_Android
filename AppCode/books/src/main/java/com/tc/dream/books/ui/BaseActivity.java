package com.tc.dream.books.ui;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.tc.dream.books.R;

/**
 * 应用主界面
 * Created by dream on 15/11/21.
 */
public class BaseActivity extends TabActivity{

    private static final String BASEACTIVITY = "BaseActivity";
    private TabHost tabHost;
    private LayoutInflater layoutInflater;
    private ImageView imageView;

    String[] mTitle = new String[] { "推荐", "心愿", "发现","我的"};
    int[] mIcon = new int[] { R.drawable.recommend, R.drawable.wishing,
            R.drawable.founding, R.drawable.mine};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initTabView();
    }

    public View getTabItemView(int i){
        View view = layoutInflater.inflate(R.layout.tab_widget_item, null);
        imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mIcon[i]);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTitle[i]);
        return view;

    }

    private void initTabView() {
        tabHost = getTabHost();
        layoutInflater = LayoutInflater.from(this);
        TabHost.TabSpec spec;

        //推荐
        Intent intent1 = new Intent(this, RecommendActivity.class);
        spec = tabHost.newTabSpec(mTitle[0]).setIndicator( getTabItemView(0) ).setContent(intent1);
        imageView.setImageResource(R.mipmap.recommend_press);
        tabHost.addTab(spec);
        //心愿
        Intent intent2 = new Intent(this, WishingActivity.class);
        spec = tabHost.newTabSpec(mTitle[1]).setIndicator( getTabItemView(1) ).setContent(intent2);
//        imageView.setImageResource(R.mipmap.wishing_press);
        tabHost.addTab(spec);
        //发现
        Intent intent3 = new Intent(this, FoundingActivity.class);
        spec = tabHost.newTabSpec(mTitle[2]).setIndicator( getTabItemView(2) ).setContent(intent3);
//        imageView.setImageResource(R.mipmap.founding_press);
        tabHost.addTab(spec);
        //我的
        Intent intent4 = new Intent(this, MineActivity.class);
        spec = tabHost.newTabSpec(mTitle[3]).setIndicator( getTabItemView(3) ).setContent(intent4);
//        imageView.setImageResource(R.mipmap.mine_press);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);

    }


}
