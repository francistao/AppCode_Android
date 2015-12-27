package com.tc.dream.books.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tc.dream.books.R;
import com.tc.dream.books.adapter.GiveAdapter;
import com.tc.dream.books.adapter.RecommendAdapter;
import com.tc.dream.books.adapter.RecommendGridAdapter;
import com.tc.dream.books.model.UserGiveBook;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * 推荐页面
 * Created by dream on 15/11/21.
 */
public class RecommendActivity extends Activity {

    private ImageView[] imageViews = null;
    private ImageView imageView = null;
    private ViewPager advPager = null;
    private AtomicInteger what = new AtomicInteger(0);
    private boolean isContinue = true;

    private GridView grid_main;
    private RecommendGridAdapter adapter;
    private static String []titles = {"设置中心1","设置中心2","设置中心3","设置中心4","设置中心5","设置中心6","设置中心7","设置中心8","设置中心9"};
//    private ListView lvBookList;
//    private RecommendAdapter recommendAdapter;

//    private SwipeRefreshLayout swipeLayout;
    private List<UserGiveBook> bookList = new ArrayList<UserGiveBook>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        initViewPager();
        initView();
//        //获取书籍数据
//        getBookItemData();
    }

    private void initView() {
        grid_main = (GridView) findViewById(R.id.grid_main);
        adapter = new RecommendGridAdapter(this, titles);
        grid_main.setAdapter(adapter);
        grid_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    /**
     * 设置广告栏的图片及切换效果
     */
    private void initViewPager() {
        advPager = (ViewPager) findViewById(R.id.adv_pager);
        // 图片列表
        List<View> advPics = new ArrayList<View>();
        // 图片1
        ImageView img1 = new ImageView(this);
        img1.setBackgroundResource(R.mipmap.book_qiaobusizhuan);
        advPics.add(img1);
        // 图片2
        ImageView img2 = new ImageView(this);
        img2.setBackgroundResource(R.mipmap.book_bainiangudu);
        advPics.add(img2);
        // 图片3
        ImageView img3 = new ImageView(this);
        img3.setBackgroundResource(R.mipmap.book_waerdenghu);
        advPics.add(img3);
        // 图片4
        ImageView img4 = new ImageView(this);
        img4.setBackgroundResource(R.mipmap.book_daodejin);
        advPics.add(img4);

        // group是R.layou.mainview中的负责包裹小圆点的LinearLayout.
        ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
        imageViews = new ImageView[advPics.size()];

        for (int i = 0; i < advPics.size(); i++) {
            imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(20, 20));
            imageView.setPadding(20, 0, 20, 0);
            imageViews[i] = imageView;
            if (i == 0) {
                // 默认选中第一张图片
                imageViews[i]
                        .setBackgroundResource(R.drawable.banner_dian_focus);
            } else {
                imageViews[i]
                        .setBackgroundResource(R.drawable.banner_dian_blur);
            }
            group.addView(imageViews[i]);
        }

        advPager.setAdapter(new AdvAdapter(advPics));
        advPager.setOnPageChangeListener(new GuidePageChangeListener());
        //按下时不继续定时滑动,弹起时继续定时滑动
        advPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        isContinue = false;
                        break;
                    case MotionEvent.ACTION_UP:
                        isContinue = true;
                        break;
                    default:
                        isContinue = true;
                        break;
                }
                return false;
            }
        });
        // 定时滑动线程
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    if (isContinue) {
                        viewHandler.sendEmptyMessage(what.get());
                        whatOption();
                    }
                }
            }

        }).start();
    }

    /**
     * 操作圆点轮换变背景
     */
    private void whatOption() {
        what.incrementAndGet();
        if (what.get() > imageViews.length - 1) {
            what.getAndAdd(-4);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {

        }
    }

    /**
     * 处理定时切换广告栏图片的句柄
     */
    private final Handler viewHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            advPager.setCurrentItem(msg.what);
            super.handleMessage(msg);
        }

    };
    /** 指引页面改监听器 */
    private final class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int arg0) {
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[arg0]
                        .setBackgroundResource(R.drawable.banner_dian_focus);
                if (arg0 != i) {
                    imageViews[i]
                            .setBackgroundResource(R.drawable.banner_dian_blur);
                }
            }

        }

    }

    /**
     * 广告栏PaperView 图片适配器
     *
     * @author wx
     *
     */
    private final class AdvAdapter extends PagerAdapter {
        private List<View> views = null;

        public AdvAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(views.get(arg1));
        }

        @Override
        public void finishUpdate(View arg0) {

        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(views.get(arg1), 0);
            return views.get(arg1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

    }








//    private void initView() {
//        lvBookList = (ListView) findViewById(R.id.lv_book_list);
////        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.lv_refresh);
////        swipeLayout.setOnRefreshListener(this);
////        swipeLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
////                android.R.color.holo_orange_light, android.R.color.holo_red_light);
//        recommendAdapter = new RecommendAdapter(RecommendActivity.this, (ArrayList<UserGiveBook>)bookList);
//        lvBookList.setAdapter(recommendAdapter);
//        lvBookList.setOnItemClickListener(this);
//    }
//
//
//    @Override
//    public void onRefresh() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                swipeLayout.setRefreshing(false);
//                //获取书籍数据
//                getBookItemData();
//            }
//        }, 1000);
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//    }
//
//    private void getBookItemData() {
//        BmobQuery<UserGiveBook> query = new BmobQuery<UserGiveBook>();
//        query.order("-updatedAt");
//        query.findObjects(this, new FindListener<UserGiveBook>() {
//            @Override
//            public void onSuccess(List<UserGiveBook> list) {
//                if (list.size() == 0) {
//                    toast("还没有书籍加入心愿哦");
//                } else {
//                    bookList = list;
//                    //通知Adapter更新数据
//                    recommendAdapter.refresh((ArrayList<UserGiveBook>) bookList);
//                }
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                toast("查询失败");
//            }
//        });
//    }



    private void toast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    };

}
